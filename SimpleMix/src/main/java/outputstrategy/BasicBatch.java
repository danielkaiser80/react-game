package outputstrategy;

import architectureinterface.OutputStrategyInterface;
import framework.Implementation;
import message.Message;
import message.Reply;
import message.Request;

/**
 * Basic implementation class of component <code>OutputStrategy</code>.
 * <p>
 * This class implements a batch output strategy.
 * <p>
 * It collects messages until an certain number of messages is collected.
 * 
 * @author Karl-Peter Fuchs
 */
public class BasicBatch extends Implementation implements OutputStrategyInterface {

	private SimplexBatch requestBatch;
	private SimplexBatch replyBatch;

	@Override
	public void constructor() {
		int batchSize = 4;
		this.requestBatch = new SimplexBatch(controller, batchSize, true);
		this.replyBatch = new SimplexBatch(controller, batchSize, false);
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
		requestBatch.addMessage((Message) request);
	}

	@Override
	public void addReply(Reply reply) {
		replyBatch.addMessage((Message) reply);
	}

	@Override
	public String[] getCompatibleImplementations() {
		return (new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler", "keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor", "externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase", "message.BasicMessage" });
	}
}