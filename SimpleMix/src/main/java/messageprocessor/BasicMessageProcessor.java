package messageprocessor;

import java.security.KeyPair;

import architectureinterface.MessageProcessorInterface;
import framework.Implementation;
import framework.Settings;
import message.Reply;
import message.Request;

/**
 * Takes messages from component <code>InputOutputHandler</code>, processes them
 * (recoding, checking for replays, removing/adding padding, initiating message
 * authentication) and bypasses them to component <code>OutputStrategy</code>.
 * <p>
 * Can handle <code>Request</code>s and <code>Replies</code> in parallel.
 * <p>
 * The functions mentioned above can be performed in parallel as well, except
 * for detecting replays (for security reasons).
 * 
 * @author Daniel Kaiser, Karl-Peter Fuchs
 *
 */
public class BasicMessageProcessor extends Implementation
		implements MessageProcessorInterface {

	@SuppressWarnings("unused")
	@Override
	public void begin() {

		ReplayDetection replayDetection = new ReplayDetection();

		for (int i = 0; i < getNumberOfThreads(); i++) {

			/*
			 * Note: Each Thread gets its own "Recoder". Therefore, recoding is
			 * performed in parallel. In contrast, both Threads get (a reference
			 * on) the same "ReplayDetection", since detecting replays can't be
			 * parallelized.
			 */
			new RequestMixThread(replayDetection);
			new ReplyMixThread(new Recoder());
		}
	}

	@Override
	public String[] getCompatibleImplementations() {
		return new String[] { "outputStrategy.BasicBatch", "outputStrategy.BasicPool",
				"inputOutputHandler.BasicInputOutputHandler",
				"keyGenerator.BasicKeyGenerator",
				"messageProcessor.BasicMessageProcessor",
				"externalInformationPort.BasicExternalInformationPort",
				"networkClock.BasicSystemTimeClock", "userDatabase.BasicUserDatabase",
				"message.BasicMessage" };
	}

	@Override
	public void constructor() {
		/** EMPTY */
	}

	/**
	 * Returns the number of message that shall be processed in parallel (as
	 * specified in property file).
	 * 
	 * @return Number of message that shall be processed in parallel.
	 */
	private static int getNumberOfThreads() {

		int numberOfThreads = Settings.getProperties()
				.getPropertyAsInt("NUMBER_OF_THREADS");

		// -1 means "automatic detection"
		return (numberOfThreads == -1) ? Runtime.getRuntime().availableProcessors()
				: numberOfThreads;

	}

	/**
	 * Thread, which coordinates the mixing process of request. Can be
	 * instantiated several times, to increase mix performance on systems with
	 * several processing units.
	 * <p>
	 * Included functionality:
	 * <ul>
	 * <li>taking (unprocessed) requests from <code>InputOutputHandler</code>
	 * <li>decrypting requests
	 * <li>validating integrity
	 * <li>detecting replays
	 * <li>passing (the now processed) requests to the
	 * <code>OutputStrategy</code> component.
	 * </ul>
	 * <p>
	 * See <code>ReplyMixThread</code> for the thread coordinating replies.
	 * 
	 * @author Karl-Peter Fuchs
	 */
	private final class RequestMixThread extends Thread {
		
		ReplayDetection replayDetection;

		/**
		 * Saves references on the bypassed objects and calls <code>start()
		 * </code>.
		 * 
		 * @param recoder
		 *            The <code>Recoder</code> that shall be used to decrypt
		 *            messages.
		 * @param replayDetection
		 *            <code>ReplayDetection</code> used to detect replays.
		 */
		RequestMixThread(ReplayDetection replayDetection) {
			this.replayDetection=replayDetection;
			start();
		}

		/**
		 * Coordinates the mixing process of request.
		 * <p>
		 * Included functionality:
		 * <ul>
		 * <li>taking (unprocessed) requests from
		 * <code>InputOutputHandler</code>
		 * <li>decrypting requests
		 * <li>validating integrity
		 * <li>detecting replays
		 * <li>passing (the now processed) requests to the
		 * <code>OutputStrategy</code> component.
		 * </ul>
		 */
		@Override
		public void run() {

			while (true) { // process messages

				Request request = getInputOutputHandler().getRequest(); // blocks
				// until
				// request
				// is
				// available
				request = (Request) Recoder.recode(request);

				if (request != null && isMACCorrect()
						&& !replayDetection.isReplay(request))
					getOutputStrategy().addRequest(request);

			}

		}

		/**
		 * @param request
		 *            the request of which the MAC should be calculated -
		 *            currently not implemented
		 */
		private boolean isMACCorrect() {
			return true;
		}

	}

	/**
	 * Thread, which coordinates the mixing process of replies. Can be
	 * instantiated several times, to increase mix performance on systems with
	 * several processing units.
	 * <p>
	 * Included functionality:
	 * <ul>
	 * <li>taking (unprocessed) replies from <code>InputOutputHandler</code>
	 * <li>encrypting replies
	 * <li>passing (the now processed) replies to the
	 * <code>OutputStrategy</code> component.
	 * </ul>
	 * <p>
	 * See <code>RequestMixThread</code> for the thread coordinating requests.
	 * 
	 * @author Karl-Peter Fuchs
	 */
	private final class ReplyMixThread extends Thread {

		/**
		 * Saves references on the bypassed object and calls <code>start()
		 * </code>.
		 * 
		 * @param recoder
		 *            The <code>Recoder</code> that shall be used to encrypt
		 *            messages.
		 */
		ReplyMixThread(Recoder recoder) {
			start();
		}

		/**
		 * Coordinates the mixing process of replies.
		 * <p>
		 * Included functionality:
		 * <ul>
		 * <li>taking (unprocessed) replies from <code>InputOutputHandler</code>
		 * <li>encrypting replies
		 * <li>passing (the now processed) replies to the
		 * <code>OutputStrategy</code> component.
		 * </ul>
		 */
		@Override
		public void run() {

			while (true) { // process messages

				Reply reply = getInputOutputHandler().getReply();
				// blocks until reply is available

				reply = (Reply) Recoder.recode(reply);
				getOutputStrategy().addReply(reply);
			}
		}
	}

	@Override
	public void initialize(KeyPair keyPair) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}
}