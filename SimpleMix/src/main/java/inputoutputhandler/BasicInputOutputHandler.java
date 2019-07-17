package inputoutputhandler;

import architectureinterface.InputOutputHandlerInterface;
import framework.Implementation;
import message.Reply;
import message.Request;
import message.TestReply;
import message.TestRequest;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Basic implementation of component <code>InputOutputHandler</code>. Implements
 * the architecture interface <code>InputOutputHandlerInterface</code>.
 * <p>
 * Handles communication with clients, other mixes and receivers.
 *
 * @author Karl-Peter Fuchs, Daniel Kaiser
 */
public class BasicInputOutputHandler extends Implementation implements InputOutputHandlerInterface {

	/**
	 * A <code>ConcurrentLinkedQueue</code>, that stores <code>Request</code>s
	 * until they are processed.
	 */
	private final ConcurrentLinkedQueue<Request> requestInputQueue = new ConcurrentLinkedQueue<>();

	/**
	 * A <code>ConcurrentLinkedQueue</code>, that stores already processed
	 * <code>Request</code>s until they are sent (to the next mix or server).
	 */
	private final ConcurrentLinkedQueue<Request> requestOutputQueue = new ConcurrentLinkedQueue<>();

	/**
	 * A <code>ConcurrentLinkedQueue</code>, that stores <code>Reply</code>ies
	 * until they are processed.
	 */
	private final ConcurrentLinkedQueue<Reply> replyInputQueue = new ConcurrentLinkedQueue<>();

	/**
	 * A <code>ConcurrentLinkedQueue</code>, that stores already processed
	 * <code>Reply</code>ies until they are sent (to the previous mix or
	 * client).
	 */
	private final ConcurrentLinkedQueue<Reply> replyOutputQueue = new ConcurrentLinkedQueue<>();

	/**
	 * empty constructor method, never used
	 */
	@Override
	public void constructor() {/** EMPTY */
	}

	@Override
	public void initialize() {/** EMPTY */
	}

	@Override
	public void begin() {
		simulateDistantProxy();
		simulateClient();

	}

	@Override
	public void addRequest(Request request) {

		synchronized (this.requestOutputQueue) {

			this.requestOutputQueue.add(request);

			// notify waiting "getRequest()" about the newly received message
			this.requestOutputQueue.notify();

		}
	}

	@Override
	public void addRequests(Request[] requests) {
		for (final Request request : requests)
			addRequest(request);
	}

	/**
	 * Returns an (already mixed) <code>Request</code> from the
	 * <code>requestOutputQueue</code>. If no requests are available, this
	 * method blocks until a new <code>Request</code> arrives.
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 *
	 * @return An (already mixed) <code>Request</code>.
	 */
	protected Request getProcessedRequest() {

		synchronized (this.requestOutputQueue) {

			Request request;

			while ((request = this.requestOutputQueue.poll()) == null) {

				try {

					// wait for new messages in the requestOutputQueue
					// = wait for notification at "addRequest()"
					this.requestOutputQueue.wait();

				} catch (final InterruptedException e) {
					System.err.println("The request was interrupted. " + e.getMessage());
					continue;
				}
			}
			return request;
		}
	}

	/**
	 * Adds the bypassed (just received) <code>Request</code> to the
	 * <code>requestInputQueue</code> (from where it will be taken by component
	 * <code>MessageProcessor</code> via <code>getRequest()</code>).
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 *
	 * @param request
	 *            Just received message, that shall be added.
	 */
	protected void addUnprocessedRequest(Request request) {

		synchronized (this.requestInputQueue) {

			this.requestInputQueue.add(request);

			// notify waiting "getRequest()" about the newly received message
			this.requestInputQueue.notify();

		}

	}

	/**
	 * Returns a <code>Request</code> (previously received, unprocessed) from a
	 * communication partner (e. g. client or other mix). If no
	 * <code>Request</code>s are available, this method blocks until a new
	 * <code>Request</code> arrives.
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 *
	 * @return A (previously received, unprocessed) <code>Request</code>.
	 */
	@Override
	public Request getRequest() {

		synchronized (this.requestInputQueue) {

			Request request;

			// as long as the forwardInputQueue is empty
			while ((request = this.requestInputQueue.poll()) == null) {

				try {

					// wait for new messages in the requestInputQueue
					// = wait for notification at "addRequest()"
					this.requestInputQueue.wait();

				} catch (final InterruptedException e) {

					System.err.println("The request was interrupted. " + e.getMessage());
					continue;
				}
			}
			return request;
		}
	}

	/**
	 * Adds the bypassed (already mixed) <code>Reply</code> to the
	 * <code>replyOutputQueue</code> (from where it will be sent to its
	 * destination).
	 * <p>
	 * Returns immediately (asynchronous behavior), the process of sending
	 * itself may be deferred (e. g. if communication channel is busy).
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 * <p>
	 * Used by component <code>OutputStrategy</code>.
	 *
	 * @param reply
	 *            Already processed message, that shall be sent to the next
	 *            communication partner.
	 *
	 * @see #addReplies(Reply[])
	 */
	@Override
	public void addReply(Reply reply) {

		synchronized (this.replyOutputQueue) {

			this.replyOutputQueue.add(reply);

			// notify waiting "getReply()" about the newly received message
			this.replyOutputQueue.notify();

		}

	}

	/**
	 * Adds all the bypassed (already mixed) <code>Reply</code>ies to the
	 * <code>replyOutputQueue</code> (from where they will be sent to their
	 * destination).
	 * <p>
	 * Returns immediately (asynchronous behavior), the process of sending
	 * itself may be deferred (e. g. if communication channel is busy).
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 * <p>
	 * Used by component <code>OutputStrategy</code>.
	 *
	 * @param replies
	 *            Already processed messages, that shall be sent to the next
	 *            communication partner.
	 *
	 * @see #addReply(Reply)
	 */
	@Override
	public void addReplies(Reply[] replies) {

		for (final Reply reply : replies) {

			addReply(reply);

		}

	}

	/**
	 * Returns an (already mixed) <code>Reply</code> from the
	 * <code>replyOutputQueue</code>. If no replies are available, this method
	 * blocks until a new <code>Reply</code> arrives.
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 *
	 * @return An (already mixed) <code>Reply</code>.
	 */
	protected Reply getProcessedReply() {

		synchronized (this.replyOutputQueue) {

			Reply reply = null;

			// as long as the replyOutputQueue is empty
			while ((reply = this.replyOutputQueue.poll()) == null) {

				try {

					// wait for new messages in the replyOutputQueue
					// = wait for notification at "addReply()"
					this.replyOutputQueue.wait();

				} catch (final InterruptedException e) {
					System.err.println("The request was interrupted. " + e.getMessage());

					continue;
				}
			}
			return reply;
		}
	}

	/**
	 * Adds the bypassed (just received) <code>Reply</code> to the
	 * <code>replyInputQueue</code> (from where it will be taken by component
	 * <code>MessageProcessor</code> via <code>getReply()</code>).
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 *
	 * @param message
	 *            Message just received, that shall be added.
	 */
	protected void addUnprocessedReply(Reply message) {

		synchronized (this.replyInputQueue) {

			this.replyInputQueue.add(message);

			// notify waiting "getReply()" about the newly received message
			this.replyInputQueue.notify();

		}

	}

	/**
	 * Returns a <code>Reply</code> (previously received, unprocessed) from a
	 * communication partner (e. g. proxy or other mix). If no
	 * <code>Reply</code>ies are available, this method blocks until a new
	 * <code>Reply</code> arrives.
	 * <p>
	 * Assures order (queuing strategy) and is thread-safe.
	 *
	 * @return A (previously received, unprocessed) <code>Reply</code>.
	 */
	@Override
	public Reply getReply() {

		synchronized (this.replyInputQueue) {

			Reply reply = null;

			// as long as the replyInputQueue is empty
			while ((reply = this.replyInputQueue.poll()) == null) {

				try {

					// wait for new messages in the replyInputQueue
					// = wait for notification at "addReply()"
					this.replyInputQueue.wait();

				} catch (final InterruptedException e) {

					System.err.println("The request was interrupted. " + e.getMessage());
					continue;

				}
			}
			return reply;
		}
	}

	@Override
	public String[] getCompatibleImplementations() {
		return new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler", "keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor", "externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase", "message.BasicMessage" };
	}

	/**
	 * This method simulates a distant proxy and is used for testing purposes
	 * only.
	 */
	private void simulateDistantProxy() {

		// receive requests and send them back immediately (=echo)
		new Thread( // simple testroutine
				() -> {
					while (true) {
						getProcessedRequest();
						System.out.println("echo");
						addUnprocessedReply(new TestReply(getMix()));
					}
				}).start();

	}

	/**
	 * This method simulates a client and is used for testing purposes only.
	 */
	private void simulateClient() {

		// send requests:
		new Thread( // simple test routine
				new Runnable() {
					int sendingRate = 100; // ms

					@Override
					public void run() {
						while (true) {
							try {
								Thread.sleep(this.sendingRate);
							} catch (final InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("sending message");
							addUnprocessedRequest(new TestRequest(getMix()));
						}
					}
				}).start();

		// receive replies:
		new Thread( // simple test routine
				() -> {
					while (true) {
						getReply();
						System.out.println("message received");
					}
				}).start();

	}

}
