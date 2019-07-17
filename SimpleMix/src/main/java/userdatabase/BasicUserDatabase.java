package userdatabase;

import architectureinterface.UserDatabaseInterface;
import exception.UnknownUserException;
import exception.UserAlreadyExistingException;
import framework.Implementation;

import java.util.Collection;

/**
 * Basic implementation class of component <code>UserDatabase</code>. Implements
 * the architecture interface <code>UserDatabaseInterface</code>.
 * <p>
 * Used to store user-specific data (e. g. identifiers, session keys and
 * buffers).
 * 
 * @author Karl-Peter Fuchs
 */
public class BasicUserDatabase extends Implementation implements UserDatabaseInterface {

	@Override
	public void addUser(User user) throws UserAlreadyExistingException { /** EMPTY */
	}

	@Override
	public void removeUser(int identifier) throws UnknownUserException { /** EMPTY */
	}

	@Override
	public User getUser(int identifier) throws UnknownUserException { /** EMPTY */
		return null;
	}

	@Override
	public User getUserByNextMixIdentifier(int nextMixIdentifier) throws UnknownUserException { /** EMPTY */
		return null;
	}

	@Override
	public boolean isExistingUser(int identifier) { /** EMPTY */
		return false;
	}

	@Override
	public int getSize() { /** EMPTY */
		return 0;
	}

	@Override
	public Collection<User> getActiveUsers() { /** EMPTY */
		return null;
	}

	@Override
	public void constructor() { /** EMPTY */
	}

	@Override
	public void initialize() { /** EMPTY */
	}

	@Override
	public void begin() { /** EMPTY */
	}

	@Override
	public String[] getCompatibleImplementations() {
		return new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler", "keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor", "externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase", "message.BasicMessage" };
	}
}