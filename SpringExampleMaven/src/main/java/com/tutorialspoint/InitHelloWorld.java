package com.tutorialspoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Class to initialize the app, this inits also other beans, not only HelloWorld
 * 
 * @author DEDKAIS4
 */
public class InitHelloWorld implements BeanPostProcessor {

	/***
	 * Logger for logging all events
	 */
	private static final Log LOGGER = LogFactory.getLog(InitHelloWorld.class.getName());

	private InitHelloWorld() {
		/** EMPTY **/
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
		LOGGER.info("BeforeInitialization : " + beanName);
		return bean; // you can return any other object as well
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) {
		LOGGER.info("AfterInitialization : " + beanName);
		return bean; // you can return any other object as well
	}

}