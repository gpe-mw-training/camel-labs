package com.redhat.gpe.training.osgi.client;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Greeter service client
 */
@Component(name = GreeterClient.SERVICE_PID,immediate = true)
public class GreeterClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterClient.class);
    protected static final String SERVICE_PID = "greeterclient";

    // OSGi Greeter service reference
    @Reference
    private Greeter greeterService;

    // default request message
    @Property(name="DEFAULT_REQUEST", label ="Default Request message", value = "I'm the Greeter Client")
    private String defaultRequest;

    @Activate
    public void init(Map<String, ?> conf) throws Exception {
        defaultRequest = (String)conf.get("DEFAULT_REQUEST");
        LOGGER.info("Greeter Client Initialized with a default request : \"" + defaultRequest + "\"");

        // invoke
        LOGGER.info("Invoking the service ...");
        invokeGreeterService("Who are you?");
        invokeGreeterService("How are you?");
        invokeGreeterService(defaultRequest);
    }

    /**
     * Utility method for invoking the OSGi Greeter service, with small delays for easier demonstration.
     */
    private void invokeGreeterService(String message) throws Exception {
        Thread.sleep(500);
        LOGGER.info("Calling Service : '" + message + "'");
        Thread.sleep(500);
        try {
            String response = greeterService.sayHello(message);
            LOGGER.info("Received response : '" + response + "'");
        } catch (Exception ex) {
            LOGGER.error("Error : " + ex.getMessage());
        }
    }

    @Deactivate
    public void destroy() throws Exception {
        LOGGER.info("Shutting down the GreeterClient...");
    }

}
