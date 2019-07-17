package com.tutorialspoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class HelloWorld2 {
	private String message;

	/***
	 * Logger for logging all events
	 */
	private static final Log LOGGER = LogFactory.getLog(HelloWorld2.class.getName());

	private HelloWorld2() {
		/** EMPTY **/
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void getMessage() {
		LOGGER.info("Your Message 2: " + message);
	}

	public static void init() {
		LOGGER.info("HelloWorld2 is going through init.");
	}

	public void destroy() {
		message = "";
		LOGGER.info("HelloWorld2 will destroy now.");
	}
}