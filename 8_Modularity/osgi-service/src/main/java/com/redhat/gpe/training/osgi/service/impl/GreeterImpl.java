package com.redhat.gpe.training.osgi.service.impl;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Greeter service implementation
 */
@Component(name = GreeterImpl.SERVICE_PID)
@Service(Greeter.class)
public class GreeterImpl implements Greeter {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterImpl.class);
    protected static final String SERVICE_PID = "greeterservice";

    // common messages
    private static final String whoRequest = "Who are you?";
    private static final String whoResponse = "I'm your OSGi greeter service, welcome!";
    private static final String howRequest = "How are you?";
    private static final String howResponse = "I'm in great form, thanks for asking!";

    // default response message
    @Property(name = "DEFAULT_RESPONSE", label="Default Response message", value = "Hi there from the OSGi greeter service...")
    private String defaultResponse;

    @Activate
    public void init(Map<String, ?> conf) throws Exception {
        defaultResponse = (String)conf.get("DEFAULT_RESPONSE");
        LOGGER.info("Greeter Service initialized with default response : \"" + defaultResponse + "\"");
    }

    /**
     * Checks the incoming request message and returns an appropriate response message.
     *
     **/
    public String sayHello(String message) {
        LOGGER.info("Received message '" + message + "'");
        String response;
        if (message.equalsIgnoreCase(whoRequest)) {
            response = whoResponse;
        } else if (message.equalsIgnoreCase(howRequest)) {
            response = howResponse;
        } else {
            response = defaultResponse;
        }
        LOGGER.info("Returning response '" + response + "'");
        return response;
    }

    @Deactivate
    public void destroy() throws Exception {
        LOGGER.info("Shutting down yhe Greeter Service ...");
    }

}
