package firstproject;

import firstproject.services.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/***
 * Spring tutorial class Hello
 */
public final class Hello {

    /***
     * Logger for logging all events
     */
    private static final Logger logger = LoggerFactory.getLogger(Hello.class);

    private Hello() {
    }

    /**
     * The main method to run the program
     *
     * @param args not used
     */
    public static void main(final String... args) {

        // loading the definitions from the given XML file
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {

            final HelloWorldService service = context.getBean(HelloWorldService.class);
            String message = service.sayHello();
            logger.info(message);

            // set a new name
            service.setName("Spring");
            message = service.sayHello();
            logger.info(message);
        }
    }
}
