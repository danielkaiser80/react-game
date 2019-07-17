package outputstrategy;

import framework.Controller;
import message.Message;
import message.Reply;
import message.Request;

import java.security.SecureRandom;

/**
 * The SimplexBasicPool is a component of the <code>BasicPool</code>, which does
 * the handling of the messages.
 * 
 * @author Karl-Peter Fuchs
 *
 */
public class SimplexBasicPool {

	private boolean isRequestPool;
	private Message[] collectedMessages;
	private int poolSize;
	private int nextFreeSlot = 0;
	private static SecureRandom secureRandom = new SecureRandom();
	private Controller controller;

	/**
	 * constructor to establish a new SimplexBatch
	 * 
	 * @param controller
	 *            link to the controller class
	 * @param poolSize
	 *            size of the pool before it is given out
	 * @param isRequestPool
	 *            is this batch a request batch?
	 */
	public SimplexBasicPool(Controller controller, boolean isRequestPool, int poolSize) {

		this.collectedMessages = new Message[poolSize];
		this.isRequestPool = isRequestPool;
		this.poolSize = poolSize;
		this.controller = controller;

	}

	/**
	 * Can be used to add a <code>Message</code>, that shall be put out
	 * according to the pool strategy.
	 * 
	 * @param mixMessage
	 *            <code>Message</code>, that shall be put out according to the
	 *            pool output strategy.
	 */
	public synchronized void addMessage(Message mixMessage) {

		if (nextFreeSlot < poolSize) {

			collectedMessages[nextFreeSlot++] = mixMessage;

		} else {

			int chosen = secureRandom.nextInt(poolSize + 1);

			if (chosen == poolSize) {

				putOutMessage(mixMessage);

			} else {

				putOutMessage(collectedMessages[chosen]);
				collectedMessages[chosen] = mixMessage;

			}

		}

	}

	/**
	 * Can be used to put out a <code>Message</code>.
	 * 
	 * @param mixMessage
	 *            <code>Message</code>, that shall be put out.
	 */
	private void putOutMessage(Message mixMessage) {

		if (isRequestPool)
			controller.getInputOutputHandler().addRequest((Request) mixMessage);
		else
			controller.getInputOutputHandler().addReply((Reply) mixMessage);

	}

}