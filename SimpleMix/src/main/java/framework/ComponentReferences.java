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
 * the interface <code>ComponentReferences</code> provides access to all the
 * controllers, the logger and the settings class of a specific mix and to the
 * mix itself
 * 
 * @author Daniel Kaiser
 *
 */
public interface ComponentReferences {

	/**
	 * @return the <code>InputOutputHandlerController</code> of the mix
	 */
	public InputOutputHandlerController getInputOutputHandler();

	/**
	 * @return the <code>OutputStrategyController</code> of the mix
	 */
	public OutputStrategyController getOutputStrategy();

	/**
	 * @return the <code>KeyGeneratorController</code> of the mix
	 */
	public KeyGeneratorController getKeyGenerator();

	/**
	 * @return the <code>MessageProcessorController</code> of the mix
	 */
	public MessageProcessorController getMessageProcessor();

	/**
	 * @return the <code>ExternalInformationPortController</code> of the mix
	 */
	public ExternalInformationPortController getExternalInformationPort();

	/**
	 * @return the <code>NetworkClockController</code> of the mix
	 */
	public NetworkClockController getNetworkClock();

	/**
	 * @return the <code>UserDatabaseController</code> of the mix
	 */
	public UserDatabaseController getUserDatabase();

	/**
	 * @return the <code>Logger</code> of the mix
	 */
	public Logger getLogger();

	/**
	 * @return the <code>Settings</code> of the mix
	 */
	public Settings getSettings();

	/**
	 * @return the instance of class <code>Mix</code> associated with the other
	 *         components
	 */
	public Mix getMix();

}