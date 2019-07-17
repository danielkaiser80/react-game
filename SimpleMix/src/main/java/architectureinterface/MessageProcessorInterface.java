package architectureinterface;

import java.security.KeyPair;

/**
 * Architecture interface for component <code>MessageProcessor</code>. The
 * <code>MessageProcessor</code> takes messages from the
 * <code>InputOutputHandler</code>, processes them and bypasses them to the
 * component <code>OutputStrategy</code>.
 * 
 * @author Daniel Kaiser
 */
public interface MessageProcessorInterface {

	/**
	 * Initialization method for the <code>MessageProcessor</code> component.
	 * Makes this component process messages (= take messages from component
	 * <code>InputOutputHandler</code>, processes them (recoding, checking for
	 * replays, removing/adding padding, initiating message authentication) and
	 * bypass them to the <code>OutputStrategy</code> component.
	 * 
	 * @param keyPair
	 *            Reference on this mix' KeyPair. Used for asymmetric
	 *            cryptography.
	 */
	public void initialize(KeyPair keyPair);

}
