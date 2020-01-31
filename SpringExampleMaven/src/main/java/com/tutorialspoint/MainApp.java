package com.tutorialspoint;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * app for testing purposes
 *
 * @author DEDKAIS4
 */
public final class MainApp {

    private MainApp() {
    }

    /**
     * main method to run the application
     *
     * @param args not used
     */
    public static void main(final String... args) {

        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("com.Beans.xml")) {

            final HelloWorld obj = context.getBean(HelloWorld.class);
            obj.getMessage();

            final HelloWorld2 obj2 = context.getBean(HelloWorld2.class);
            obj2.getMessage();

            context.registerShutdownHook();
        }
    }
}
