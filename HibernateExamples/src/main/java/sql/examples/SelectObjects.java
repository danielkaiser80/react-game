package sql.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Example class for selecting objects from SQLite
 *
 * @author dkaiser
 */
class SelectObjects {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectObjects.class);

    private SelectObjects() {/*EMPTY*/}

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

            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Contact;")) {
                while (rs.next()) {
                    final int id = rs.getInt("id");
                    final String name = rs.getString("name");
                    final int age = rs.getInt("age");
                    final String address = rs.getString("address");
                    final float salary = rs.getFloat("salary");
                    LOGGER.info("ID = {}", id);
                    LOGGER.info("NAME = {}", name);
                    LOGGER.info("AGE = {}", age);
                    LOGGER.info("ADDRESS = {}", address);
                    LOGGER.info("SALARY = {}", salary);
                }
            }

            stmt.close();
            c.commit();

        } catch (final SQLException e) {
            LOGGER.error("An SQL error occurred.", e);
            System.exit(0);
        }
    }
}