package framework;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Logs and displays output and error messages.
 * <p>
 * Log files are stored in the folder "logfiles".
 * 
 * @author Karl-Peter Fuchs
 */
public class Logger {

	/**
	 * init the logger, set up a <code>DuplicatePrintStream</code> for
	 * <code>System.out</code> and <code>System.err</code>
	 * 
	 * @param filenameAndPathForLogfile
	 *            the folder "logfiles", where the logs shall be put into
	 */
	public static void init(String filenameAndPathForLogfile) {

		try (PrintStream filePrintStream = new PrintStream(new FileOutputStream(filenameAndPathForLogfile));
				PrintStream duplicateOutPrintStream = new DuplicatePrintStream(System.out, filePrintStream, false);
				PrintStream duplicateErrPrintStream = new DuplicatePrintStream(System.err, filePrintStream, true);) {
			System.setOut(duplicateOutPrintStream);
			System.setErr(duplicateErrPrintStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}