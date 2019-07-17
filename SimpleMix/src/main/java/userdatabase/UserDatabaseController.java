package userdatabase;

import architectureinterface.UserDatabaseInterface;
import exception.UnknownUserException;
import exception.UserAlreadyExistingException;
import framework.Controller;
import framework.LocalClassLoader;
import framework.Mix;

import java.util.Collection;

/**
 * Controller class of component <code>UserDatabase</code>. Implements the
 * architecture interface <code>UserDatabaseInterface</code>.
 * <p>
 * Used to store user-specific data (e. g. identifiers, session keys and
 * buffers).
 * 
 * @author Karl-Peter Fuchs
 */
public class UserDatabaseController extends Controller implements UserDatabaseInterface {

	/**
	 * the <code>userDatabaseImplementation</code> stores the actual
	 * implementation of the <code>userDatabase</code>
	 */
	private UserDatabaseInterface userDatabaseImplementation;

	/**
	 * the constructor calls the mix to register this controller with the mix
	 * 
	 * @param mix
	 *            the mix which owns this controller
	 */
	public UserDatabaseController(Mix mix) {
		super(mix);
	}

	@Override
	public void addUser(User user) throws UserAlreadyExistingException {
		userDatabaseImplementation.addUser(user);
	}

	@Override
	public void removeUser(int identifier) throws UnknownUserException {
		userDatabaseImplementation.removeUser(identifier);
	}

	@Override
	public User getUser(int identifier) throws UnknownUserException {
		return userDatabaseImplementation.getUser(identifier);
	}

	@Override
	public User getUserByNextMixIdentifier(int nextMixIdentifier) throws UnknownUserException {
		return userDatabaseImplementation.getUserByNextMixIdentifier(nextMixIdentifier);
	}

	@Override
	public boolean isExistingUser(int identifier) {
		return userDatabaseImplementation.isExistingUser(identifier);
	}

	@Override
	public int getSize() {
		return userDatabaseImplementation.getSize();
	}

	@Override
	public Collection<User> getActiveUsers() {
		return userDatabaseImplementation.getActiveUsers();
	}

	/**
	 * method in which the specific <code>userDatabaseImplementation</code> is
	 * loaded and instantiated
	 */
	@Override
	public void instantiateSubclass() {
		userDatabaseImplementation = LocalClassLoader.instantiateUserDatabaseImplementation(this);
	}

}
