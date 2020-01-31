package com.tutorialspoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/***
 * Spring tutorial program "HelloWorld"
 *
 */
public final class HelloWorld {

    private String message;

    /***
     * Logger for logging all events
     */
    private static final Log LOGGER = LogFactory.getLog(HelloWorld.class);

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
        LOGGER.info("Your Message: " + message);
    }

    /***
     * Init the bean
     */
    public void init() {
        s = "Bean is going through init.";
        LOGGER.info(s);
    }

    /***
     * destroy the bean
     */
    public void destroy() {
        s = "Bean will destroy now.";
        LOGGER.info(s);
    }
}