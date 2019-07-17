package externalinformationport;

import architectureinterface.ExternalInformationPortInterface;
import exception.InformationRetrieveException;
import framework.Controller;
import framework.LocalClassLoader;
import framework.Mix;

import java.net.InetAddress;

/**
 * Controller class of component <code>ExternalInformationPort</code>.
 * Implements the architecture interface
 * <code>ExternalInformationPortInterface</code>.
 * <p>
 * Used for <code>Information</code> (for example a public key) exchange with
 * "external" communication partners (for example other mixes or clients). This
 * component doesn't affect the sending of mix messages directly (That's done by
 * the <code>InputOutputHandler</code>: see
 * <code>architectureInterface.InputOutputHandlerInterface</code>).
 * <p>
 * Each exchangeable type of information is specified in the enumeration
 * <code>Information</code>.
 * 
 * @author Karl-Peter Fuchs
 */
public class ExternalInformationPortController extends Controller implements ExternalInformationPortInterface {

	/**
	 * the <code>externalInformationPortImplementation</code> stores the actual
	 * implementation of the <code>ExternalInformationPort</code>
	 */
	private ExternalInformationPortInterface externalInformationPortImplementation;

	/**
	 * the constructor calls the mix to register this controller with the mix
	 * 
	 * @param mix
	 *            the mix which owns this controller
	 */
	public ExternalInformationPortController(Mix mix) {
		super(mix);
	}

	@Override
	public Object getInformation(InetAddress informationProviderAddress, int informationProviderPort,
			Information informationOfInterest) throws InformationRetrieveException {
		return this.externalInformationPortImplementation.getInformation(informationProviderAddress, informationProviderPort,
				informationOfInterest);
	}

	@Override
	public Object getInformation(InetAddress informationProviderAddress, int informationProviderPort,
			Information informationOfInterest, byte[] data) throws InformationRetrieveException {
		return this.externalInformationPortImplementation.getInformation(informationProviderAddress, informationProviderPort,
				informationOfInterest, data);
	}

	@Override
	public Object[] getInformationFromAll(InetAddress informationProviderAddress, int informationProviderPort,
			Information informationOfInterest) throws InformationRetrieveException {
		return this.externalInformationPortImplementation.getInformationFromAll(informationProviderAddress,
				informationProviderPort, informationOfInterest);
	}

	@Override
	public Object[] getInformationFromAll(InetAddress informationProviderAddress, int informationProviderPort,
			Information informationOfInterest, byte[] data) throws InformationRetrieveException {
		return this.externalInformationPortImplementation.getInformationFromAll(informationProviderAddress,
				informationProviderPort, informationOfInterest, data);
	}

	/**
	 * method in which the specific
	 * <code>externalInformationPortImplementation</code> is loaded and
	 * instantiated
	 */
	@Override
	public void instantiateSubclass() {
		this.externalInformationPortImplementation = LocalClassLoader
				.instantiateExternalInformationPortImplementation(this);
	}

}