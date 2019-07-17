package outputstrategy;

import architectureinterface.OutputStrategyInterface;
import framework.Controller;
import framework.LocalClassLoader;
import framework.Mix;
import message.Reply;
import message.Request;

/**
 * Controller class of component <code>OutputStrategy</code>.
 * <p>
 * Collects messages until an output criterion is fulfilled (certain number of
 * messages collected or timeout reached).
 * <p>
 * Messages are added by component <code>MessageProcessor</code>. When the
 * output criterion is fulfilled, the collected messages are bypassed to the
 * <code>InputOutputHandler</code> (component), which sends them to their
 * destination.
 * 
 * @author Karl-Peter Fuchs
 */
public class OutputStrategyController extends Controller implements OutputStrategyInterface {

	/**
	 * the constructor calls the mix to register this controller with the mix
	 * 
	 * @param mix
	 *            the mix which owns this controller
	 */
	public OutputStrategyController(Mix mix) {
		super(mix);
	}

	/**
	 * the <code>outputStrategyImplementation</code> stores the actual
	 * implementation of the <code>outputStrategy</code>
	 */
	private OutputStrategyInterface outputStrategyImplementation;

	@Override
	public void addRequest(Request request) {
		outputStrategyImplementation.addRequest(request);
	}

	@Override
	public void addReply(Reply reply) {
		outputStrategyImplementation.addReply(reply);
	}

	/**
	 * method in which the specific <code>outputStrategyImplementation</code> is
	 * loaded and instantiated
	 */
	@Override
	public void instantiateSubclass() {
		this.outputStrategyImplementation = LocalClassLoader.instantiateOutputStrategyImplementation(this);
	}

}