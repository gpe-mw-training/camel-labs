package com.redhat.gpe.training.greet;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Greeter service client
 */
@Component
public class GreeterClient {
    private static final Logger LOG = LoggerFactory.getLogger(GreeterClient.class);

    // OSGi Greeter service reference
    @Reference
    private Greeter greeterService;

    // default request message
    @Property(name="DEFAULT_REQUEST", label ="Default Request message", value = "I'm the Greeter Client")
    private String defaultRequest;

    @Activate
    public void init() throws Exception {
        LOG.info("Initialized.");
        // invoke
        LOG.info("Invoking...");
        invokeGreeterService("Who are you?");
        invokeGreeterService("How are you?");
        invokeGreeterService(defaultRequest);
    }

    /**
     * Utility method for invoking the OSGi Greeter service, with small delays for easier demonstration.
     */
    private void invokeGreeterService(String message) throws Exception {
        Thread.sleep(1000);
        LOG.info("Sending message : '" + message + "'");
        Thread.sleep(1000);
        try {
            String response = greeterService.sayHello(message);
            LOG.info("Received response : '" + response + "'");
        } catch (Exception ex) {
            LOG.error("Error : " + ex.getMessage());
        }
    }

    @Deactivate
    public void destroy() throws Exception {
        LOG.info("Shutting down...");
        LOG.info("Finished.");
    }

}
