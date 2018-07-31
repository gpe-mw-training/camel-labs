package com.redhat.gpte.training.camel;

import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringMainApp {

    private static Logger logger = LoggerFactory.getLogger(SpringMainApp.class);

    public static void main(String[] args) throws Exception {

        // create a Main instance
        Main main = new Main();

        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();

        // set Spring application context
        main.setApplicationContextUri("META-INF/spring/spring-camel-context.xml");

        // run until you terminate the JVM
        logger.info("Starting Spring Camel. Use ctrl + c to terminate the JVM.\n");

        main.run();
    }

}
