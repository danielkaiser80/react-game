package org.hibernate.dialect.identity;

/**
 * Class to support identity columns in SQLite database
 * @author dkaiser
 *
 */
public class SQLiteDialectIdentityColumnSupport extends IdentityColumnSupportImpl {
	@Override
	public boolean supportsIdentityColumns() {
		return true;
	}

	@Override
	public boolean hasDataTypeInIdentityColumn() {
		// As specified in NHibernate dialect
		return false;
	}

	@Override
	public String getIdentitySelectString(String table, String column, int type) {
		return "select last_insert_rowid()";
	}

	@Override
	public String getIdentityColumnString(int type) {
		return "integer";
	}
}