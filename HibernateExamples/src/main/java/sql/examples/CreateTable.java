package sql.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example to create a table in an SQLite database
 *
 * @author dkaiser
 */
class CreateTable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTable.class);

    private CreateTable() {
        /* EMPTY */
    }

    /**
     * Main method to start the program
     *
     * @param args not used
     */
    public static void main(String... args) {

        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement stmt = c.createStatement()) {
            Class.forName("org.sqlite.JDBC");

            final String sql = "CREATE TABLE Contact " +
                    "(id       INTEGER PRIMARY KEY     NOT NULL, " +
                    "name     TEXT    NOT NULL, " +
                    "age      INT     NOT NULL, " +
                    "address  CHAR(50), " +
                    "salary   REAL)";
            stmt.executeUpdate(sql);
            stmt.close();

        } catch (final ClassNotFoundException e) {
            LOGGER.error(e.getClass().getName() + ": " + e.getMessage(), e);
            System.exit(0);
        } catch (final SQLException e) {
            LOGGER.error("An SQL error occured. " + e.getMessage(), e);
            System.exit(0);
        }
        LOGGER.info("Opened database successfully");
    }
}