package gui;

/**
 * This <code>enum</code> stores all Component names of a mix and is used to
 * build the GUI. The names are case sensitive as these are used to access data
 * on the file system.
 * 
 * @author Daniel Kaiser
 *
 */
public enum MixComponent {
	/**
	 * Enumeration value to represent the component
	 * <code>ExternalInformationPort</code>
	 */
	EXTERNAL_INFORMATIONPORT,
	/**
	 * Enumeration value to represent the component
	 * <code>InputOutputHandler</code>
	 */
	INPUT_OUTPUT_HANDLER,
	/**
	 * Enumeration value to represent the component <code>KeyGenerator</code>
	 */
	KEY_GENERATOR,
	/**
	 * Enumeration value to represent the component
	 * <code>MessageProcessor</code>
	 */
	MESSAGE_PROCESSOR,
	/**
	 * Enumeration value to represent the component <code>NetworkClock</code>
	 */
	NETWORK_CLOCK,
	/**
	 * Enumeration value to represent the component <code>OutputStrategy</code>
	 */
	OUTPUT_STRATEGY,
	/**
	 * Enumeration value to represent the component <code>UserDatabase</code>
	 */
	USER_DATABASE;

	private String implementationName;

	/**
	 * generate the packageName from the ComponentName, every letter has to be
	 * small, all underscores removed
	 * 
	 * @return name of the component's package
	 */
	public String getPackageName() {
		return name().replaceAll("_", "").toLowerCase();
	}

	/**
	 * returns the capitalized name of the ComponentName
	 * 
	 * @return the capitalized name of the ComponentName
	 */
	public String capitalizedName() {
		return name().toUpperCase();
	}

	/**
	 * Setter for the actual name of the implementation of the component
	 * 
	 * @param implementationName
	 *            name of the implementation of the component
	 */
	void setImplementationName(String implementationName) {
		this.implementationName = implementationName;
	}

	/**
	 * Getter for the actual name of the implementation of the component
	 * 
	 * @return name of the implementation of the component
	 */
	public String getImplementationName() {
		return implementationName;
	}
}