package outputstrategy;

import framework.Controller;
import message.Message;
import message.Reply;
import message.Request;

/**
 * The SimplexBatch is a component of the <code>BasicBatch</code>, which does
 * the handling of the messages.
 * 
 * @author Karl-Peter Fuchs
 *
 */
public class SimplexBatch {

	private boolean isRequestBatch;
	private int batchSize;
	private Message[] collectedMessages;
	private int nextFreeSlot = 0;
	private Controller controller;

	/**
	 * constructor to establish a new SimplexBatch
	 * 
	 * @param controller
	 *            link to the controller class
	 * @param batchSize
	 *            size of the batch before it is given out
	 * @param isRequestBatch
	 *            is this batch a request batch?
	 */
	public SimplexBatch(Controller controller, int batchSize, boolean isRequestBatch) {

		this.batchSize = batchSize;
		this.isRequestBatch = isRequestBatch;
		this.collectedMessages = new Message[batchSize];
		this.controller = controller;

	}

	/**
	 * Can be used to add a <code>Message</code>, that shall be put out
	 * according to the batch strategy.
	 * <p>
	 * Return immediately (asynchronous behavior), internal output decision is
	 * deferred.
	 * 
	 * @param mixMessage
	 *            <code>Message</code>, that shall be put out according to the
	 *            batch output strategy.
	 */
	public synchronized void addMessage(Message mixMessage) {

		collectedMessages[nextFreeSlot++] = mixMessage;

		if (nextFreeSlot == batchSize) {

			System.out.println("putting out batch"); // TODO: remove
			for (Message m : collectedMessages)
				if (isRequestBatch)
					controller.getInputOutputHandler().addRequest((Request) m);
				else
					controller.getInputOutputHandler().addReply((Reply) m);

			nextFreeSlot = 0;

		}

	}

}