package inputoutputhandler;

import architectureinterface.InputOutputHandlerInterface;
import framework.Controller;
import framework.LocalClassLoader;
import framework.Mix;
import message.Reply;
import message.Request;

/**
 * Controller class of component <code>InputOutputHandler</code>. Implements the
 * architecture interface <code>InputOutputHandlerInterface</code>.
 * <p>
 * Handles communication with clients, other mixes and receivers.
 * 
 * @author Karl-Peter Fuchs
 */
public class InputOutputHandlerController extends Controller implements InputOutputHandlerInterface {

	/**
	 * the <code>inputOutputHandlerImplementation</code> stores the actual
	 * implementation of the <code>inputOutputHandler</code>
	 */
	private InputOutputHandlerInterface inputOutputHandlerImplementation;

	/**
	 * the constructor calls the mix to register this controller with the mix
	 * 
	 * @param mix
	 *            the mix which owns this controller
	 */
	public InputOutputHandlerController(Mix mix) {
		super(mix);
	}

	/**
	 * method in which the specific
	 * <code>inputOutputHandlerImplementation</code> is loaded and instantiated
	 */
	@Override
	public void instantiateSubclass() {
		this.inputOutputHandlerImplementation = LocalClassLoader.instantiateInputOutputHandlerImplementation(this);
	}

	@Override
	public Reply getReply() {
		return inputOutputHandlerImplementation.getReply();
	}

	@Override
	public Request getRequest() {
		return inputOutputHandlerImplementation.getRequest();
	}

	@Override
	public void addRequest(Request request) {
		inputOutputHandlerImplementation.addRequest(request);
	}

	@Override
	public void addRequests(Request[] requests) {
		inputOutputHandlerImplementation.addRequests(requests);
	}

	@Override
	public void addReply(Reply reply) {
		inputOutputHandlerImplementation.addReply(reply);
	}

	@Override
	public void addReplies(Reply[] replies) {
		inputOutputHandlerImplementation.addReplies(replies);
	}

}