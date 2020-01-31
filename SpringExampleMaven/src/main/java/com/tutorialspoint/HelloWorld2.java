package com.tutorialspoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HelloWorld2 {
    private String message;

    /***
     * Logger for logging all events
     */
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld2.class);

    private HelloWorld2() {
        /* EMPTY */
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void getMessage() {
        logger.info("Your Message 2: {}", message);
    }

    public static void init() {
        logger.info("HelloWorld2 is going through init.");
    }

    public void destroy() {
        message = "";
        logger.info("HelloWorld2 will destroy now.");
    }
}
