package framework;

import architectureinterface.*;

/**
 * The LocalClassLoader extends the standard Java <code>ClassLoader</code> and
 * is used to load the specific implementation classes into the
 * <code>framework</code> and for testing purposes into the GUI.
 * 
 * <p>
 * In Java there is a hierarchy of the ClassLoaders, which has to be considered
 * when adding a new ClassLoader.
 * 
 * <p>
 * This class furthermore contains methods to instantiate
 * <code>Implementation</code>s. *
 * 
 * @author Daniel Kaiser
 *
 */
public class LocalClassLoader extends ClassLoader {

	/**
	 * The instantiated <code>localClassLoader</code> is needed, when the class
	 * could not be loaded by the <code>SystemClassLoader</code>.
	 */
	private static LocalClassLoader localClassLoader;

	/**
	 * This method instantiates a <code>OutputStrategy</code> implementation
	 * specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static OutputStrategyInterface instantiateOutputStrategyImplementation(Controller controller) {

		Implementation implementation = instantiateImplementation(Settings.getProperty("OUTPUT_STRATEGY"),
				"outputStrategy");

		if (!(implementation instanceof OutputStrategyInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("OUTPUT_STRATEGY")
					+ " must implement the interface \"OutputStrategy.java\"!");

		implementation.setController(controller);
		return (OutputStrategyInterface) implementation;

	}

	/**
	 * This method instantiates an <code>InputOutputHandler</code>
	 * implementation specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static InputOutputHandlerInterface instantiateInputOutputHandlerImplementation(Controller controller) {

		Implementation implementation = instantiateImplementation(Settings.getProperty("INPUT_OUTPUT_HANDLER"),
				"inputOutputHandler");

		if (!(implementation instanceof InputOutputHandlerInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("INPUT_OUTPUT_HANDLER")
					+ " must implement the interface \"InputOutputHandler.java\"!");

		implementation.setController(controller);
		return (InputOutputHandlerInterface) implementation;

	}

	/**
	 * This method instantiates an <code>ExternalInformationPort</code>
	 * implementation specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static ExternalInformationPortInterface instantiateExternalInformationPortImplementation(
			Controller controller) {

		Implementation implementation = instantiateImplementation(Settings.getProperty("EXTERNAL_INFORMATION_PORT"),
				"externalInformationPort");

		if (!(implementation instanceof ExternalInformationPortInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("EXTERNAL_INFORMATION_PORT")
					+ " must implement the interface \"ExternalInformationPort.java\"!");

		implementation.setController(controller);
		return (ExternalInformationPortInterface) implementation;

	}

	/**
	 * This method instantiates a <code>KeyGenerator</code> implementation
	 * specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static KeyGeneratorInterface instantiateKeyGeneratorImplementation(Controller controller) {

		// System.out.println(Settings.getProperty("KEY_GENERATOR"));

		Implementation implementation = instantiateImplementation(Settings.getProperty("KEY_GENERATOR"),
				"keyGenerator");

		if (!(implementation instanceof KeyGeneratorInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("KEY_GENERATOR")
					+ " must implement the interface \"KeyGenerator.java\"!");

		implementation.setController(controller);
		return (KeyGeneratorInterface) implementation;

	}

	/**
	 * This method instantiates a <code>MessageProcessor</code> implementation
	 * specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static MessageProcessorInterface instantiateMessageProcessorImplementation(Controller controller) {

		Implementation implementation = instantiateImplementation(Settings.getProperty("MESSAGE_PROCESSOR"),
				"messageProcessor");

		if (!(implementation instanceof MessageProcessorInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("MESSAGE_PROCESSOR")
					+ " must implement the interface \"MessageProcessor.java\"!");

		implementation.setController(controller);
		return (MessageProcessorInterface) implementation;

	}

	/**
	 * This method instantiates a <code>NetworkClock</code> implementation
	 * specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static NetworkClockInterface instantiateNetworkClockImplementation(Controller controller) {

		Implementation implementation = instantiateImplementation(Settings.getProperty("NETWORK_CLOCK"),
				"networkClock");

		if (!(implementation instanceof NetworkClockInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("NETWORK_CLOCK")
					+ " must implement the interface \"NetworkClock.java\"!");

		implementation.setController(controller);
		return (NetworkClockInterface) implementation;

	}

	/**
	 * This method instantiates a <code>UserDatabase</code> implementation
	 * specified by in the property file.
	 * 
	 * @param controller
	 *            class is needed, so that the <code>Implementation</code> knows
	 *            about its <code>Controller</code>
	 * @return instantiated class
	 */
	public static UserDatabaseInterface instantiateUserDatabaseImplementation(Controller controller) {

		Implementation implementation = instantiateImplementation(Settings.getProperty("USER_DATABASE"),
				"userDatabase");

		if (!(implementation instanceof UserDatabaseInterface))
			throw new RuntimeException("ERROR: " + Settings.getProperty("USER_DATABASE")
					+ " must implement the interface \"UserDatabase.java\"!");

		implementation.setController(controller);
		return (UserDatabaseInterface) implementation;

	}

	/**
	 * This method instantiates an implementation specified by a class name and
	 * a package name.
	 * 
	 * @param className
	 *            class name to instantiate
	 * @param packageName
	 *            package from which the class should be instantiated
	 * @return instantiated class
	 */
	public static Implementation instantiateImplementation(String className, String packageName) {

		try {

			String binaryName = packageName + "." + className;

			Object o = newInstance(binaryName);

			if (!(o instanceof Implementation))
				throw new RuntimeException(
						"ERROR: " + className + " must be a child of the class \"framework.Implementation.java\"!");

			return (Implementation) o;

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("ERROR: could not load " + className + "\nwrong name in property file?");

		}

	}

	/**
	 * This method instantiates an object of a specified class.
	 * 
	 * @param binaryClassName
	 *            class to instantiate
	 * @return a new instance of an object of the class
	 * @throws Exception
	 *             when class could not be instantiated
	 */
	public static Object newInstance(String binaryClassName) throws Exception {

		Class<?> r = loadClassFile(binaryClassName);
		return r.newInstance();

	}

	/**
	 * This method is used to load a class file from the file system into the
	 * framework.
	 * 
	 * First, the <code>LocalClassLoader</code> tries to load the class using
	 * the <code>SystemClassLoader</code>, after that, the
	 * <code>localClassLoader</code> object itself. This uses the method
	 * <code>loadClass</code> of the super class <code>ClassLoader</code> which
	 * is not overwritten.
	 * 
	 * @param binaryClassName
	 *            name of the class to load
	 * @return instantiated class
	 * @throws ClassNotFoundException
	 *             Exception, when class is not found
	 */
	public static Class<?> loadClassFile(String binaryClassName) throws ClassNotFoundException {

		String normalizedClassName = normalizeBinaryClassName(binaryClassName);

		if (getSystemClassLoader().loadClass(normalizedClassName) != null)
			return getSystemClassLoader().loadClass(normalizedClassName);
		localClassLoader = new LocalClassLoader();
		return localClassLoader.loadClass(normalizedClassName, true);
	}

	/**
	 * This method normalizes a class name and deletes the ending.
	 * 
	 * @param binaryClassName
	 *            class name to normalize
	 * @return normalizes class name
	 */
	public static String normalizeBinaryClassName(String binaryClassName) {
		if (binaryClassName.endsWith(".class"))
			return binaryClassName.replace(".class", "");
		if (binaryClassName.endsWith(".java"))
			return binaryClassName.replace(".java", "");
		return binaryClassName;
	}
}