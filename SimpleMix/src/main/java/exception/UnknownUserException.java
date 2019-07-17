package exception;

/**
 * Exception thrown by <code>UserDatabase</code> when <code>UserDatabase
 * </code> is supposed to perform actions on a user object with an unknown
 * identifier ("unknown" = identifier not in database).
 * 
 * @see userdatabase
 * 
 * @author Karl-Peter Fuchs
 */
public final class UnknownUserException extends Exception {

	/** The serialVersionUID as identifier for this serializable class. */
	private static final long serialVersionUID = 567772345678899458L;

	/**
	 * Constructs an InvalidPortException (empty constructor).
	 */
	public UnknownUserException() { /** EMPTY */
	}

	/**
	 * Returns the String "No suiting user in database!"
	 * 
	 * @return "No suiting user in database!".
	 */
	@Override
	public String getMessage() {
		return "No suiting user in database!";
	}

	/**
	 * Returns the String "UnknownUserException".
	 * 
	 * @return "UnknownUserException"
	 */
	@Override
	public String toString() {
		return "UnknownUserException";
	}
}