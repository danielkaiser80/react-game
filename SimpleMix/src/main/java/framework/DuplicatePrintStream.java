package framework;

import java.io.PrintStream;

/**
 * PrintStream that writes all data provided to it to two PrintStreams, ie. it
 * duplicates the stream.
 * 
 * @author Daniel Kaiser
 *
 */
public class DuplicatePrintStream extends PrintStream {

	private PrintStream outputStream2;
	private String prefix;

	/**
	 * This constructor sets up the two output PrintStreams.
	 * 
	 * @param outputStream1
	 *            first output PrintStream
	 * @param outputStream2
	 *            second output PrintStream
	 * @param isErrorStream
	 *            special handling of the error stream
	 * 
	 */
	public DuplicatePrintStream(PrintStream outputStream1, PrintStream outputStream2, boolean isErrorStream) {

		super(outputStream1);
		this.outputStream2 = outputStream2;
		this.prefix = isErrorStream ? "ERROR:" : "";

	}

	/**
	 * write data to the output streams
	 * 
	 */
	@Override
	public void write(byte buf[], int off, int len) {

		try {

			super.write(buf, off, len);
			outputStream2.write(buf, off, len);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * flush the output streams
	 * 
	 */
	@Override
	public void flush() {

		super.flush();
		outputStream2.flush();

	}

	/**
	 * print a line to the output streams
	 * 
	 */
	@Override
	public void println(String s) {
		super.println(prefix + System.currentTimeMillis() + ": " + s);
	}

}