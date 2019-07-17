package firstproject;

import firstproject.services.HelloWorldService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/***
 * Spring tutorial class Hello
 *
 */
public final class Hello {

	/***
	 * Logger for logging all events
	 */
	private static final Log LOGGER = LogFactory.getLog(Hello.class.getName());

	private Hello() {
	}

	/**
	 * The main method to run the program
	 * @param args not used
	 */
	public static void main(final String... args) {

		// loading the definitions from the given XML file
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {

			final HelloWorldService service = (HelloWorldService) context.getBean("helloWorldService");
			String message = service.sayHello();
			LOGGER.info(message);

			// set a new name
			service.setName("Spring");
			message = service.sayHello();
			LOGGER.info(message);
		}
	}
}