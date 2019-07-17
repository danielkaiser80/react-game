package framework;

/**
 * Provides properties from the file <code>Properties.properties</code> and
 * provides methods to manipulate these properties.
 * 
 * @author Daniel Kaiser
 */
public class Settings {

	public static boolean DEBUG_ON;
	private static Settings settings = new Settings();

	/** <code>MixProperties</code> object to load values from. */
	private static MixProperties properties = new MixProperties();

	/**
	 * Empty constructor. Never used since all methods are static.
	 */
	private Settings() {
	}

	/**
	 * load properties from file system
	 * 
	 * @param propertyFile
	 *            to load
	 * @return properties
	 */
	public static MixProperties initialize(String propertyFile) {

		properties.load(propertyFile);
		DEBUG_ON = properties.getProperty("DEBUG_OUTPUT").equals("ON");

		return properties;

	}

	/**
	 * Returns this <code>Settings</code> object.
	 * 
	 * @return This <code>Settings</code> object.
	 */
	public static Settings getSettings() {
		return settings;
	}

	/**
	 * Sets the property with the specified key in the <code>property
	 * </code> object to the bypassed value.
	 * 
	 * @param key
	 *            The key to be placed into the property list.
	 * @param value
	 *            The value corresponding to the key.
	 * @return The previous value of the specified key, or null if it did not
	 *         have one.
	 */
	public static Object setProperty(String key, String value) {

		return properties.setProperty(key, value);

	}

	/**
	 * Searches and returns the property with the specified key in the
	 * <code>property</code> object.
	 * 
	 * @param key
	 *            The property key.
	 * @return Property with the specified key in the <code>property
	 * 				</code> object.
	 */
	public static String getProperty(String key) {

		return properties.getProperty(key);

	}

	/**
	 * Returns this <code>Settings</code>' <code>Properties</code> object.
	 * 
	 * @return This <code>Settings</code>' <code>Properties</code> object.
	 */
	public static MixProperties getProperties() {

		return properties;

	}

	/**
	 * Returns the current configuration as a String.
	 * 
	 * @return Current configuration as a String.
	 */
	public static String getConfig() {

		String config = Settings.getProperties().toString();
		config = config.replace('{', ' ');
		config = config.replace(',', '\n');
		config = config.replace(' ', ' ');
		config = config.replace('}', ' ');

		return config;

	}

}
