package exception;

public class MixException extends Exception {

	/** The serialVersionUID as identifier for this serializable class. */
	private static final long serialVersionUID = -8490000467933929605L;

	private final String message;

	/**
	 * Constructs an MixException with a message.
	 * 
	 * @param message
	 *            the message of the Exception
	 */
	public MixException(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * Returns the String "UnknownUserException".
	 * 
	 * @return "UnknownUserException"
	 */
	@Override
	public String toString() {
		return "MixException: " + message;
	}
}