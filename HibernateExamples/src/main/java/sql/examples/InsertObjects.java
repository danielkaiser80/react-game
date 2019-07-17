package sql.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example class for object insertion into SQLite database
 *
 * @author dkaiser
 */
class InsertObjects {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsertObjects.class);
    private static final String INSERT_STATEMENT_FORMAT =
            "INSERT INTO Contact (NAME, AGE, ADDRESS, SALARY) VALUES (%s);";

    private InsertObjects() {
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

            c.setAutoCommit(false);
            LOGGER.info("Opened database successfully");

            String sql = String.format(INSERT_STATEMENT_FORMAT, "'Paul', 32, 'California', 20000.00");
            stmt.executeUpdate(sql);

            sql = String.format(INSERT_STATEMENT_FORMAT, "'Allen', 25, 'Texas', 15000.00");
            stmt.executeUpdate(sql);

            sql = String.format(INSERT_STATEMENT_FORMAT, "'Teddy', 23, 'Norway', 20000.00");
            stmt.executeUpdate(sql);

            sql = String.format(INSERT_STATEMENT_FORMAT, "'Mark', 25, 'Rich-Mond ', 65000.00");
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();

        } catch (final SQLException e) {
            LOGGER.error("An SQL error occured. " + e.getMessage(), e);
            System.exit(0);
        }
    }
}