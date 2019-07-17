package keygenerator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import architectureinterface.KeyGeneratorInterface;
import framework.Implementation;
import framework.Settings;

/**
 * Generates cryptographic keys.
 * 
 * @author Karl-Peter Fuchs
 */
public class BasicKeyGenerator extends Implementation implements KeyGeneratorInterface {

	/**
	 * <code>SecretKey</code> used to encrypt (header) data between two mixes.
	 * Key size and type are specified in the property file.
	 */
	public static final int INTER_MIX_KEY = 1;

	/**
	 * Initialization vector (<code>IvParameterSpec</code>) used to encrypt
	 * (header) data between two mixes.
	 */
	public static final int INTER_MIX_IV = 2;

	/**
	 * <code>KeyPair</code> (public key and a private key) used for asymmetric
	 * cryptography. Key size and type are specified in the property file.
	 */
	public static final int KEY_PAIR = 3;

	@Override
	public void constructor() {/** EMPTY */

	}

	@Override
	public void initialize() {
		/** EMPTY */
	}

	@Override
	public void begin() {
		/** EMPTY */
	}

	@Override
	public String[] getCompatibleImplementations() {
		return (new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler", "keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor", "externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase", "message.BasicMessage" });
	}

	/**
	 * Generates a cryptographic key according to the bypassed identifier.
	 * 
	 * @param identifier
	 *            Type of key to be generated (see class constants).
	 * 
	 * @return The generated cryptographic key.
	 */
	@Override
	public Object generateKey(int identifier) {

		switch (identifier) {

		case INTER_MIX_KEY:
			return generateInterMixKey();

		case INTER_MIX_IV:
			return generateInterMixIV();

		case KEY_PAIR:
			return generateKeyPair();

		default:
			return null;

		}

	}

	/**
	 * Generates and returns a <code>KeyPair</code>.
	 * 
	 * @return The generated <code>KeyPair</code>.
	 */
	protected static KeyPair generateKeyPair() {

		// load values from property file
		String asymKeyGeneratorName = Settings.getProperty("NAME_OF_ASYM_KEY_GENERATOR");

		int asymKeyLength = Settings.getProperties().getPropertyAsInt("ASYM_KEY_LENGTH");

		KeyPairGenerator keyPairGenerator = null;

		try {

			keyPairGenerator = KeyPairGenerator.getInstance(asymKeyGeneratorName);

			keyPairGenerator.initialize(asymKeyLength);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			throw new RuntimeException("(MIX) Couldn't generate keypair!");
		}

		return keyPairGenerator.generateKeyPair();

	}

	/**
	 * Generates and returns a <code>SecretKey</code>.
	 * 
	 * @return The generated <code>SecretKey</code>.
	 */
	protected static SecretKey generateInterMixKey() {

		try {

			KeyGenerator keyGenerator = KeyGenerator.getInstance(
					Settings.getProperty("NAME_OF_INTER_MIX_KEY_GENERATOR"), Settings.getProperty("CRYPTO_PROVIDER"));

			keyGenerator.init(Settings.getProperties().getPropertyAsInt("INTER_MIX_KEY_LENGTH"));

			return keyGenerator.generateKey();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR: (MIX) Couldn't generate InterMixKey!");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR: (MIX) Couldn't generate InterMixKey!");
		}

	}

	/**
	 * Generates and returns an initialization vector (
	 * <code>IvParameterSpec</code>).
	 * 
	 * @return The generated initialization vector.
	 */
	protected static IvParameterSpec generateInterMixIV() {

		int blockLength = Settings.getProperties().getPropertyAsInt("INTER_MIX_BLOCK_SIZE");
		byte[] interMixIVAsByteArray = new byte[blockLength];
		new SecureRandom().nextBytes(interMixIVAsByteArray);

		return new IvParameterSpec(interMixIVAsByteArray);

	}

}
