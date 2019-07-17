package externalinformationport;

import architectureinterface.ExternalInformationPortInterface;
import exception.InformationRetrieveException;
import framework.Implementation;

import java.net.InetAddress;

/**
 * Basic implementation of component <code>ExternalInformationPort</code>.
 * Implements the architecture interface
 * <code>ExternalInformationPortInterface</code>.
 * <p>
 * Used for <code>Information</code> (for example a public key) exchange with
 * "external" communication partners (for example other mixes or clients). This
 * component doesn't affect the sending of mix messages directly (That's done by
 * the <code>InputOutputHandler</code>: see
 * <code>architectureInterface.InputOutputHandlerInterface</code>).
 *
 * @author Karl-Peter Fuchs
 */
public class BasicExternalInformationPort extends Implementation
		implements ExternalInformationPortInterface {

	private static final Object[] nullObject = new Object[0];

	@Override
	public Object getInformation(InetAddress informationProviderAddress,
			int informationProviderPort,
			Information informationOfInterest) throws InformationRetrieveException {
		return null;
	}

	@Override
	public Object getInformation(InetAddress informationProviderAddress,
			int informationProviderPort,
			Information informationOfInterest, byte[] data)
			throws InformationRetrieveException {
		return null;
	}

	@Override
	public Object[] getInformationFromAll(InetAddress informationProviderAddress,
			int informationProviderPort,
			Information informationOfInterest) throws InformationRetrieveException {

		return nullObject;
	}

	@Override
	public Object[] getInformationFromAll(InetAddress informationProviderAddress,
			int informationProviderPort,
			Information informationOfInterest, byte[] data)
			throws InformationRetrieveException {
		return nullObject;
	}

	@Override
	public void constructor() { // EMPTY
	}

	@Override
	public void initialize() { // EMPTY
	}

	@Override
	public void begin() { // EMPTY
	}

	@Override
	public String[] getCompatibleImplementations() {
		return new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler",
				"keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor",
				"externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase",
				"message.BasicMessage" };
	}
}