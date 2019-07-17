package framework;

import externalinformationport.ExternalInformationPortController;
import inputoutputhandler.InputOutputHandlerController;
import keygenerator.KeyGeneratorController;
import messageprocessor.MessageProcessorController;
import networkclock.NetworkClockController;
import outputstrategy.OutputStrategyController;
import userdatabase.UserDatabaseController;

import java.util.logging.Logger;

/**
 * The abstract class <code>Implementation</code> implements the
 * <code>ComponentReferences</code> interface, so that a particular
 * implementation of a component knows about all <code>Controller</code>s of all
 * other components of the mix. Furthermore, it can access the other
 * controllers' implementations, the logger, the mix itself and the settings of
 * the mux.
 * 
 * 
 * @author Daniel Kaiser
 *
 */
public abstract class Implementation implements ComponentReferences {

	protected InputOutputHandlerController inputOutputHandler;
	protected OutputStrategyController outputStrategy;
	protected KeyGeneratorController keyGenerator;
	protected MessageProcessorController messageProcessor;
	protected ExternalInformationPortController externalInformationPort;
	protected NetworkClockController networkClock;
	protected UserDatabaseController userDatabase;
	protected Mix mix;
	protected Logger logger;
	protected Settings settings;
	protected Controller controller;

	/**
	 * For testing purposes, the actual implementation time of the first
	 * component is shown here.
	 */
	static {
		System.out.println(Implementation.class.getSimpleName());
	}

	/**
	 * The constructor of the implementation shows the instantiation of all
	 * particular classes.
	 */
	public Implementation() {
		System.out.println(this.getBinaryName());
	}

	@Override
	public InputOutputHandlerController getInputOutputHandler() {
		return inputOutputHandler;
	}

	@Override
	public OutputStrategyController getOutputStrategy() {
		return outputStrategy;
	}

	@Override
	public KeyGeneratorController getKeyGenerator() {
		return keyGenerator;
	}

	@Override
	public MessageProcessorController getMessageProcessor() {
		return messageProcessor;
	}

	@Override
	public ExternalInformationPortController getExternalInformationPort() {
		return externalInformationPort;
	}

	@Override
	public NetworkClockController getNetworkClock() {
		return networkClock;
	}

	@Override
	public UserDatabaseController getUserDatabase() {
		return userDatabase;
	}

	@Override
	public Mix getMix() {
		return mix;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public Settings getSettings() {
		return settings;
	}

	/**
	 * In this method, the controller of the particular implementation is set;
	 * furthermore, all components of the controller are linked with this
	 * implementation. Then the mix is registered with the implementation and
	 * the implementation is registered with the mix. Finally, the pseudo
	 * constructor of the implementation class is run.
	 * 
	 * @param controller
	 *            The controller of the implementation.
	 */
	public void setController(Controller controller) {

		this.controller = controller;
		this.inputOutputHandler = controller.getInputOutputHandler();
		this.outputStrategy = controller.getOutputStrategy();
		this.keyGenerator = controller.getKeyGenerator();
		this.messageProcessor = controller.getMessageProcessor();
		this.externalInformationPort = controller.getExternalInformationPort();
		this.networkClock = controller.getNetworkClock();
		this.userDatabase = controller.getUserDatabase();
		this.logger = controller.getLogger();
		this.settings = controller.getSettings();
		this.mix = controller.getMix();
		this.mix.registerImplementation(this);
		this.constructor();
		controller.setImplementation(this);

	}

	/**
	 * This pseudo Constructor is run after the instantiation of the
	 * implementation class by the <code>ClassLoader</code>.
	 * 
	 */
	public abstract void constructor();

	/**
	 * This method is run after the references to and from all the other
	 * components are set. Here data could be send to other components, if
	 * needed.
	 * <p>
	 * After returning from this method, the current implementation should be
	 * able to start to work.
	 * 
	 */
	public abstract void initialize();

	/**
	 * When this method is run, the component starts working.
	 * 
	 */
	public abstract void begin();

	/**
	 * This method provides all compatible implementations of a class.
	 * 
	 * @return all compatible implementations of a class
	 */
	public abstract String[] getCompatibleImplementations();

	/**
	 * This returns the binary name of the implementation class.
	 * 
	 * @return binary name of the implementation class
	 */
	public String getBinaryName() {
		return this.getClass().getName();
	}

}