package com.redhat.gpte.training.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringMainApp {

    private static Logger logger = LoggerFactory.getLogger(SpringMainApp.class);

    private ProducerTemplate template;
    public static Main main;

    public static void main(String[] args) throws Exception {
        SpringMainApp app = new SpringMainApp();
        app.boot();

        // Send message to the direct endpoint
        MessageProducer messageProducer = (MessageProducer) main.getApplicationContext().getBean("messageProducer");
        messageProducer.sendMessage();
        main.run();
    }

    public void boot() throws Exception {
        // create a Main instance
        main = new Main();

        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();

        // set Spring application context
        main.setApplicationContextUri("META-INF/spring/spring-camel-context.xml");

        // Start Camel
        main.start();
    }

}
