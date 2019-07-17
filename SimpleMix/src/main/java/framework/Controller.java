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
 * The abstract class <code>Controller</code> provides all methods, interfaces
 * and fields that are needed by every controller. This class provides a
 * skeleton for the controllers, which enables access to all other controllers
 * and the mix. Furthermore the internal classes <code>Logger</code> and
 * <code>Settings</code> are available.
 * 
 * @author Daniel Kaiser
 *
 */
public abstract class Controller implements ComponentReferences {

	protected InputOutputHandlerController inputOutputHandler;
	protected OutputStrategyController outputStrategy;
	protected KeyGeneratorController keyGenerator;
	protected MessageProcessorController messageProcessor;
	protected ExternalInformationPortController externalInformationPort;
	protected NetworkClockController networkClock;
	protected UserDatabaseController userDatabase;
	protected Logger logger;
	protected Settings settings;
	protected Implementation implementation; // specific implementation to be
												// loaded by the classloader
	protected Mix mix;

	public Controller(Mix mix) {

		this.mix = mix;
		mix.registerComponent(this);

	}

	/**
	 * @param inputOutputHandler
	 * @param outputStrategy
	 * @param keyGenerator
	 * @param messageProcessor
	 * @param externalInformationPort
	 * @param networkClock
	 * @param userDatabase
	 * @param logger
	 * @param settings
	 */
	public void setComponentReferences(InputOutputHandlerController inputOutputHandler,
			OutputStrategyController outputStrategy, KeyGeneratorController keyGenerator,
			MessageProcessorController messageProcessor, ExternalInformationPortController externalInformationPort,
			NetworkClockController networkClock, UserDatabaseController userDatabase, Logger logger,
			Settings settings) {

		this.inputOutputHandler = inputOutputHandler;
		this.outputStrategy = outputStrategy;
		this.keyGenerator = keyGenerator;
		this.messageProcessor = messageProcessor;
		this.externalInformationPort = externalInformationPort;
		this.networkClock = networkClock;
		this.userDatabase = userDatabase;
		this.logger = logger;
		this.settings = settings;

	}

	/**
	 * Initialize the component
	 */
	public void initialize() {
		this.implementation.initialize();
	}

	public void begin() {
		this.implementation.begin();
	}

	// overrides aus der Schnittstelle
	@Override
	public InputOutputHandlerController getInputOutputHandler() {
		return this.inputOutputHandler;
	}

	@Override
	public OutputStrategyController getOutputStrategy() {
		return this.outputStrategy;
	}

	@Override
	public KeyGeneratorController getKeyGenerator() {
		return this.keyGenerator;
	}

	@Override
	public MessageProcessorController getMessageProcessor() {
		return this.messageProcessor;
	}

	@Override
	public ExternalInformationPortController getExternalInformationPort() {
		return this.externalInformationPort;
	}

	@Override
	public NetworkClockController getNetworkClock() {
		return this.networkClock;
	}

	@Override
	public UserDatabaseController getUserDatabase() {
		return this.userDatabase;
	}

	@Override
	public Logger getLogger() {
		return this.logger;
	}

	@Override
	public Settings getSettings() {
		return this.settings;
	}

	@Override
	public Mix getMix() {
		return this.mix;
	}

	// Getter und Setter für die konkrete Implementierung
	public void setImplementation(Implementation implementation) {
		this.implementation = implementation;
	}

	public Implementation getImplementation() {
		return this.implementation;
	}

	/**
	 * abstract method in which the specific implementation subclasses are
	 * loaded and instantiated this method is different for all controllers as
	 * every controller has to implement another interface
	 */
	public abstract void instantiateSubclass();
}