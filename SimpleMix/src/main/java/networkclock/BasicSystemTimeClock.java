package networkclock;

import architectureinterface.NetworkClockInterface;
import framework.Implementation;

/**
 * Provides a clock.
 * <p>
 * The method <code>getTime()</code> can be used to generate timestamps.
 * <p>
 * Uses Adam Buckley's class <code>NtpMessage</code> as message format.
 * 
 * @author Karl-Peter Fuchs, Daniel Kaiser
 */
public class BasicSystemTimeClock extends Implementation implements NetworkClockInterface {

	/**
	 * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 * (The typical accuracy of a SNTP client/server exchange is fractions of a
	 * second.)
	 * 
	 * @return The number of milliseconds since January 1, 1970, 00:00:00 GMT.
	 */
	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public void constructor() {/** EMPTY */
	}

	@Override
	public void initialize() {/** EMPTY */
	}

	@Override
	public void begin() {/** EMPTY */
	}

	@Override
	public String[] getCompatibleImplementations() {
		return (new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler", "keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor", "externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase", "message.BasicMessage" });
	}
}
