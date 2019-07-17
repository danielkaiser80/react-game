package framework;

import exception.MixException;
import externalinformationport.ExternalInformationPortController;
import inputoutputhandler.InputOutputHandlerController;
import keygenerator.KeyGeneratorController;
import messageprocessor.MessageProcessorController;
import networkclock.NetworkClockController;
import outputstrategy.OutputStrategyController;
import userdatabase.UserDatabaseController;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class coordinates the components of this mix. Each component (offering
 * methods) has its own (architecture) interface (see package
 * <code>architectureInterfaces</code>) and a controller class (named
 * <code>"ComponentName"Controller</code>), loading the current implementation
 * of the according interface. Therefore, the implementation of each component
 * can change, without changes to any other component (interface and
 * implementing (controller) class remain the same).
 * <p>
 * General information: A mix is some kind of proxy that attempts to hide the
 * correspondence between its incoming and outgoing messages, as proposed by
 * David Chaum in 1981.
 * 
 * @author Karl-Peter Fuchs, Daniel Kaiser
 *
 */
public class Mix implements ComponentReferences {

	private OutputStrategyController outputStrategy;
	private ExternalInformationPortController externalInformationPort;
	private InputOutputHandlerController inputOutputHandler;
	private KeyGeneratorController keyGenerator;
	private MessageProcessorController messageProcessor;
	private NetworkClockController networkClock;
	private UserDatabaseController userDatabase;
	private Settings settings = Settings.getSettings();
	private Logger logger;
	private KeyPair keyPair;

	/**
	 * stores all components' <code>Controller</code>s
	 */
	private List<Controller> components = new ArrayList<>();

	/**
	 * stores all components' <code>Implementation</code>s
	 */
	private List<Implementation> implementations = new ArrayList<>();

	/**
	 * Constructor for the <code>Mix</code>, this constructor contains all steps
	 * to start mixing traffic. First, components are created, references are
	 * set, <code>Implementation</code>-classes are loaded and validated. After
	 * that, a <code>keyPair</code> is created which is used to initialize the
	 * components. Finally the mix starts to mix.
	 */
	public Mix() {

		// added as properties were not loaded correctly - no initialization
		Settings.initialize("Properties");

		generateComponents();
		setReferencesBetweenComponents();
		loadImplementations();
		try {
			validateImplementationComposition();
			this.keyPair = (KeyPair) this.keyGenerator.generateKey(3);

			initializeComponents();
			beginMixing();

		} catch (MixException e) {
			logger.log(Level.ALL, "The mix could not be set up.", e);
		}
	}

	/**
	 * Creates a new <code>Mix</code>.
	 * 
	 * @param args
	 *            Not used.
	 */
	@SuppressWarnings("unused")
	public static void main(String... args) {
		new Mix();
	}

	/**
	 * In this method, all components of the mix are generated.
	 * <p>
	 * Note: the order of the components is important; do not change it!
	 */
	private void generateComponents() {

		this.settings = Settings.getSettings();

		this.keyGenerator = new KeyGeneratorController(this);
		this.outputStrategy = new OutputStrategyController(this);
		this.networkClock = new NetworkClockController(this);
		this.userDatabase = new UserDatabaseController(this);
		this.messageProcessor = new MessageProcessorController(this);
		this.inputOutputHandler = new InputOutputHandlerController(this);
		this.externalInformationPort = new ExternalInformationPortController(this);

	}

	/**
	 * Every component is automatically registered in the mix as this method is
	 * called in the constructor of each controller. This is done, so that these
	 * components are accessible by all other components.
	 * 
	 * @param controllerToRegister
	 *            is the controller, which should be registered by the mix
	 */
	protected void registerComponent(Controller controllerToRegister) {
		this.components.add(controllerToRegister);
	}

	/**
	 * all controllers, that belong to this mix
	 * 
	 * @return all controllers as an array
	 */
	public Controller[] getComponents() {
		return this.components.toArray(new Controller[0]);
	}

	/**
	 * this method sets references between all the components
	 */
	private void setReferencesBetweenComponents() {

		for (Controller c : this.components)
			c.setComponentReferences(this.inputOutputHandler, this.outputStrategy,
					this.keyGenerator, this.messageProcessor,
					this.externalInformationPort, this.networkClock, this.userDatabase,
					this.logger, this.settings);

	}

	/**
	 * in this method, the corresponding <code>Implementation</code>s are loaded
	 * and instantiated.
	 */
	private void loadImplementations() {
		for (Controller c : this.components)
			c.instantiateSubclass();
	}

	/**
	 * Also the implementations should be registered with the mix, to be
	 * accessible by all components.
	 * 
	 * @param implementationToRegister
	 *            is the implementation, which should be registered by the mix
	 */
	protected void registerImplementation(Implementation implementationToRegister) {
		this.implementations.add(implementationToRegister);
	}

	/**
	 * all implementations, that belong to this mix
	 * 
	 * @return all implementations as an array
	 */
	public Implementation[] getImplementations() {
		return this.implementations.toArray(new Implementation[0]);
	}

	/**
	 * the implementation composition has to be validated, so that the mix is
	 * able to run
	 * 
	 * @throws MixException
	 *             this Exception occurs, when the mix is not able to validate
	 *             the composition
	 * 
	 */
	private void validateImplementationComposition() throws MixException {
		for (Implementation i : this.implementations)
			for (Implementation j : this.implementations)
				if (i.getBinaryName().equals(j.getBinaryName()))
					continue; // implementation always supports itself
				else if (!Util.contains(i.getBinaryName(),
						j.getCompatibleImplementations()))
					throw new MixException(
							"ERROR: invalid composition of implementations!\n"
									+ i.getBinaryName()
									+ " does not support " + j.getBinaryName());
	}

	/**
	 * This method initializes all controllers of the components.
	 * 
	 */
	private void initializeComponents() {
		for (Controller c : this.components)
			c.initialize();
	}

	/**
	 * This method starts the mixing process.
	 * 
	 */
	private void beginMixing() {
		for (Controller c : this.components)
			c.begin();
	}

	/**
	 * @return the current <code>InputOutputHandler</code>
	 */
	@Override
	public InputOutputHandlerController getInputOutputHandler() {
		return this.inputOutputHandler;
	}

	/**
	 * @return the current <code>OutputStrategy</code>
	 */
	@Override
	public OutputStrategyController getOutputStrategy() {
		return this.outputStrategy;
	}

	/**
	 * @return the current <code>KeyGenerator</code>
	 */
	@Override
	public KeyGeneratorController getKeyGenerator() {
		return this.keyGenerator;
	}

	/**
	 * @return the current <code>MessageProcessor</code>
	 */
	@Override
	public MessageProcessorController getMessageProcessor() {
		return this.messageProcessor;
	}

	/**
	 * @return the current <code>ExternalInformationPort</code>
	 */
	@Override
	public ExternalInformationPortController getExternalInformationPort() {
		return this.externalInformationPort;
	}

	/**
	 * @return the current <code>NetworkClock</code>
	 */
	@Override
	public NetworkClockController getNetworkClock() {
		return this.networkClock;
	}

	/**
	 * @return the current <code>UserDatabase</code>
	 */
	@Override
	public UserDatabaseController getUserDatabase() {
		return this.userDatabase;
	}

	/**
	 * @return the current <code>Logger</code>
	 */
	@Override
	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * @return the current <code>Settings</code> of the mix
	 */
	@Override
	public Settings getSettings() {
		return this.settings;
	}

	/**
	 * @return the current <code>Mix</code>
	 */
	@Override
	public Mix getMix() {
		return this;
	}

	/**
	 * @return the current <code>KeyPair</code>
	 */
	public KeyPair getKeyPair() {
		return this.keyPair;
	}
}