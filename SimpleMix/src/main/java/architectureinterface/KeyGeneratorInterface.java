package architectureinterface;

/**
 * Architecture interface for component <code>KeyGenerator</code>.
 * <p>
 * Used to generate cryptographic keys.
 * 
 * @author Karl-Peter Fuchs
 */
public interface KeyGeneratorInterface {

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

	/**
	 * Must generate a cryptographic keys of the specified type. The
	 * implementing component must provide <code>public</code> constants
	 * (=identifiers) for each type of key.
	 * 
	 * @param identifier
	 *            Type of key to be generated.
	 * 
	 * @return The generated key.
	 */
	public Object generateKey(int identifier);

}