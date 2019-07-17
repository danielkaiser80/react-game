package outputstrategy;

import architectureinterface.OutputStrategyInterface;
import framework.Implementation;
import message.Message;
import message.Reply;
import message.Request;

/**
 * Basic implementation class of component <code>OutputStrategy</code>.
 * <p>
 * This class implements a pool output strategy.
 * <p>
 * It collects messages until the pool is filled up to the poolsize. When
 * another message is added, one of the messages in the pool or the new message
 * are randomly selected and put out to the <code>InputOutputHandler</code>
 * (component), which sends them to their destination.
 * 
 * @author Karl-Peter Fuchs
 */
public class BasicPool extends Implementation implements OutputStrategyInterface {

	private SimplexBasicPool requestPool;
	private SimplexBasicPool replyPool;

	@Override
	public void constructor() {
		int poolSize = 4; // TODO: property file!
		this.requestPool = new SimplexBasicPool(controller, true, poolSize);
		this.replyPool = new SimplexBasicPool(controller, false, poolSize);
	}

	@Override
	public void initialize() {
		// no need to do anything
	}

	@Override
	public void begin() {
		// no need to do anything
	}

	@Override
	public void addRequest(Request request) {
		requestPool.addMessage((Message) request);
	}

	@Override
	public void addReply(Reply reply) {
		replyPool.addMessage((Message) reply);
	}

	@Override
	public String[] getCompatibleImplementations() {
		return (new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler", "keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor", "externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase", "message.BasicMessage" });
	}
}
