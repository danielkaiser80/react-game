package com.tutorialspoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Spring tutorial program "HelloWorld"
 */
public final class HelloWorld {

    private String message;

    /***
     * Logger for logging all events
     */
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    private String s = "";

    HelloWorld() {
        /* EMPTY */
    }

    /***
     * Set a new message
     * @param message the new message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    public void getMessage() {
        logger.info("Your Message: {}", message);
    }

    /***
     * Init the bean
     */
    public void init() {
        s = "Bean is going through init.";
        logger.info(s);
    }

    /***
     * destroy the bean
     */
    public void destroy() {
        s = "Bean will destroy now.";
        logger.info(s);
    }
}
