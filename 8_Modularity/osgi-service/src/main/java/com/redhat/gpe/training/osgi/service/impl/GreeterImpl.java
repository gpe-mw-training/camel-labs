package com.redhat.gpe.training.osgi.service.impl;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Greeter service implementation
 */
@Component
@Service(Greeter.class)
public class GreeterImpl implements Greeter {

    // logger
    private Logger logger = LoggerFactory.getLogger(getClass());

    // common messages
    private static final String whoRequest = "Who are you?";
    private static final String whoResponse = "I'm your OSGi greeter service, welcome!";
    private static final String howRequest = "How are you?";
    private static final String howResponse = "I'm in great form, thanks for asking!";

    // default response message
    @Property(name = "DEFAULT_RESPONSE", label="Default Response message", value = "Hi there from the OSGi greeter service...")
    private String defaultResponse;

    @Activate
    public void init() throws Exception {
        logger.info("GreeterImpl initialized.");
        logger.info("Will return response '" + defaultResponse + "'");
    }

    /**
     * Checks the incoming request message and returns an appropriate response message.
     *
     **/
    public String sayHello(String message) {
        logger.info("Received message '" + message + "'");
        String response;
        if (message.equalsIgnoreCase(whoRequest)) {
            response = whoResponse;
        } else if (message.equalsIgnoreCase(howRequest)) {
            response = howResponse;
        } else {
            response = defaultResponse;
        }
        logger.info("Returning response '" + response + "'");
        return response;
    }

    @Deactivate
    public void destroy() throws Exception {
        logger.info("Shutting down...");
    }

}
