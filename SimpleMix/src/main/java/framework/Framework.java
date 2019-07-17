package framework;

/**
 * The class <code>Framework</code> is responsible for setting up the
 * environment and loading all necessary components. Currently this class only
 * loads one mix; later several mixes should be loaded to simulate mixing.
 * 
 * @author Daniel Kaiser
 *
 */
public class Framework {

	/**
	 * initialize the Logger and load the main properties file from the file
	 * system before anything is instantiated
	 */
	static {
		Logger.init("output/" + System.currentTimeMillis() + "-consoleOutput.txt");
		Settings.initialize("Properties");
	}

	/**
	 * Start one mix.
	 *
	 * @param args
	 *            Not used.
	 */
	@SuppressWarnings("unused")
	public static void main(String... args) {
		new Mix();
	}
}
