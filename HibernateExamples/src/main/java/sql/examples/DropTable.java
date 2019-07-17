package sql.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example to drop an SQLite table
 * @author dkaiser
 *
 */
class DropTable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropTable.class);

	private DropTable() {
		/* EMPTY */}

	/**
	 * Main method to start the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String... args) {

		try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
				Statement stmt = c.createStatement()) {
			Class.forName("org.sqlite.JDBC");

			final String sql = "DROP TABLE Contact ";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (final ClassNotFoundException e) {
			LOGGER.error(e.getClass().getName() + ": " + e.getMessage(), e);
			System.exit(0);
		} catch (final SQLException e) {
			LOGGER.error("An SQL error occured. " + e.getMessage(), e);
			System.exit(0);
		}
		LOGGER.info("Dropped database successfully");
	}
}