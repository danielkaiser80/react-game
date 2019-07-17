/*
 * The author disclaims copyright to this source code.  In place of
 * a legal notice, here is a blessing:
 *
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 *
 */
package org.hibernate.dialect;

import org.hibernate.JDBCException;
import org.hibernate.ScrollMode;
import org.hibernate.dialect.function.*;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.SQLiteDialectIdentityColumnSupport;
import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.dialect.unique.DefaultUniqueDelegate;
import org.hibernate.dialect.unique.UniqueDelegate;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.hibernate.internal.util.JdbcExceptionHelper;
import org.hibernate.mapping.Column;
import org.hibernate.type.StandardBasicTypes;

import java.sql.SQLException;
import java.sql.Types;

/**
 * An SQL dialect for SQLite 3.
 */
public final class SQLiteDialect extends Dialect {

    // IDENTITY support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final SQLiteDialectIdentityColumnSupport IDENTITY_COLUMN_SUPPORT = new SQLiteDialectIdentityColumnSupport();
    // limit/offset support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final AbstractLimitHandler LIMIT_HANDLER = new AbstractLimitHandler() {
        @Override
        public boolean bindLimitParametersInReverseOrder() {
            return true;
        }

        @Override
        public String processSql(String sql, RowSelection selection) {
            final boolean hasOffset = LimitHelper.hasFirstRow(selection);
            return sql + (hasOffset ? " limit ? offset ?" : " limit ?");
        }

        @Override
        public boolean supportsLimit() {
            return true;
        }
    };
    private static final int SQLITE_BUSY = 5;
    private static final int SQLITE_LOCKED = 6;
    private static final int SQLITE_IOERR = 10;
    private static final int SQLITE_PROTOCOL = 15;
    private static final int SQLITE_TOOBIG = 18;
    private static final int SQLITE_CONSTRAINT = 19;
    private static final int SQLITE_MISMATCH = 20;
    private static final int SQLITE_NOTADB = 26;
    private static final ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter() {
        @Override
        protected String doExtractConstraintName(SQLException sqle) {
            final int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
            if (errorCode == SQLITE_CONSTRAINT) {
                return extractUsingTemplate(
                        "constraint ", " failed", sqle.getMessage());
            }
            return null;
        }
    };
    private final UniqueDelegate uniqueDelegate;

    /**
     * Create the new SQLite dialect definition
     */
    public SQLiteDialect() {
        registerColumnType(Types.BIT, "boolean");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.DOUBLE, "double");
        registerColumnType(Types.DECIMAL, "decimal");
        registerColumnType(Types.CHAR, "char");
        registerColumnType(Types.LONGVARCHAR, "longvarchar");
        registerColumnType(Types.TIMESTAMP, "datetime");
        registerColumnType(Types.BINARY, "blob");
        registerColumnType(Types.VARBINARY, "blob");
        registerColumnType(Types.LONGVARBINARY, "blob");

        registerFunction("concat",
                new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
        registerFunction("mod",
                new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1 % ?2"));
        registerFunction("quote",
                new StandardSQLFunction("quote", StandardBasicTypes.STRING));
        registerFunction("random",
                new NoArgSQLFunction("random", StandardBasicTypes.INTEGER));
        registerFunction("round", new StandardSQLFunction("round"));
        registerFunction("substr",
                new StandardSQLFunction("substr", StandardBasicTypes.STRING));
        registerFunction("trim", new SQLiteTrimFunction());
        uniqueDelegate = new SQLiteUniqueDelegate(this);
    }

    // SQLException support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static JDBCException connectionOrNullError(SQLException sqlException, String message,
                                                       String sql, int errorCode) {
        if (errorCode == SQLITE_NOTADB
                || (errorCode >= SQLITE_IOERR && errorCode <= SQLITE_PROTOCOL))
            return new JDBCConnectionException(message, sqlException, sql);
        // returning null allows other delegates to operate
        return null;
    }

    @Override
    public SQLExceptionConversionDelegate buildSQLExceptionConversionDelegate() {
        return (SQLException sqlException, String message, String sql) -> {
            int errorCode = JdbcExceptionHelper.extractErrorCode(sqlException);
            switch (errorCode) {
                case SQLITE_TOOBIG:
                case SQLITE_MISMATCH:
                    return new DataException(message, sqlException, sql);
                case SQLITE_BUSY:
                case SQLITE_LOCKED:
                    return new LockAcquisitionException(message, sqlException, sql);
                default:
                    return connectionOrNullError(sqlException, message, sql, errorCode);
            }
        };
    }

    @Override
    public boolean canCreateSchema() {
        return false;
    }

    @Override
    public ScrollMode defaultScrollMode() {
        return ScrollMode.FORWARD_ONLY;
    }

    @Override
    public boolean doesReadCommittedCauseWritersToBlockReaders() {
        return true;
    }

    @Override
    public boolean doesRepeatableReadCauseReadersToBlockWriters() {
        return true;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getAddColumnString() {
        return "add column";
    }

    @Override
    public String getAddForeignKeyConstraintString(String constraintName,
                                                   String[] foreignKey, String referencedTable,
                                                   String[] primaryKey, boolean referencesPrimaryKey) {
        throw new UnsupportedOperationException(
                "No add foreign key syntax supported by SQLiteDialect");
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        throw new UnsupportedOperationException(
                "No add primary key syntax supported by SQLiteDialect");
    }

    @Override
    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    @Override
    public String getDropForeignKeyString() {
        throw new UnsupportedOperationException(
                "No drop foreign key syntax supported by SQLiteDialect");
    }

    // union subclass support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public String getForUpdateString() {
        return "";
    }

    // DDL support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return IDENTITY_COLUMN_SUPPORT;
    }

    @Override
    public int getInExpressionCountLimit() {
        // Compile/runtime time option:
        // http://sqlite.org/limits.html#max_variable_number
        return 1000;
    }

    @Override
    public LimitHandler getLimitHandler() {
        return LIMIT_HANDLER;
    }

    @Override
    public String getSelectGUIDString() {
        return "select hex(randomblob(16))";
    }

    @Override
    public UniqueDelegate getUniqueDelegate() {
        return uniqueDelegate;
    }

    @Override
    public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
        return EXTRACTER;
    }

    @Override
    public boolean hasAlterTable() {
        // As specified in NHibernate dialect
        return false;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    @Override
    public boolean qualifyIndexName() {
        return false;
    }

    @Override
    public boolean supportsCommentOn() {
        return true;
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    // lock acquisition support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public boolean supportsLockTimeouts() {
        // may be http://sqlite.org/c3ref/db_mutex.html ?
        return false;
    }

    @Override
    public boolean supportsOuterJoinForUpdate() {
        return false;
    }

    @Override
    public boolean supportsTupleDistinctCounts() {
        return false;
    }

    @Override
    public boolean supportsUnionAll() {
        return true;
    }

    private static class SQLiteUniqueDelegate extends DefaultUniqueDelegate {
        SQLiteUniqueDelegate(Dialect dialect) {
            super(dialect);
        }

        @Override
        public String getColumnDefinitionUniquenessFragment(Column column) {
            return " unique";
        }
    }

    private final class SQLiteTrimFunction extends AbstractAnsiTrimEmulationFunction {
        SQLiteTrimFunction() {
            super();
        }

        @Override
        protected SQLFunction resolveBothSpaceTrimFromFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?2)");
        }

        @Override
        protected SQLFunction resolveBothSpaceTrimFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1)");
        }

        @Override
        protected SQLFunction resolveBothTrimFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1, ?2)");
        }

        @Override
        protected SQLFunction resolveLeadingSpaceTrimFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(?1)");
        }

        @Override
        protected SQLFunction resolveLeadingTrimFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING,
                    "ltrim(?1, ?2)");
        }

        @Override
        protected SQLFunction resolveTrailingSpaceTrimFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(?1)");
        }

        @Override
        protected SQLFunction resolveTrailingTrimFunction() {
            return new SQLFunctionTemplate(StandardBasicTypes.STRING,
                    "rtrim(?1, ?2)");
        }
    }
}