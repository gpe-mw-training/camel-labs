package com.redhat.gpe.training.osgi.service.impl;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Greeter service implementation
 */
public class GreeterImpl
        implements Greeter, InitializingBean, DisposableBean {
    // logger
    private Logger logger = LoggerFactory.getLogger(getClass());

    // common messages
    private static final String whoRequest = "Who are you?";
    private static final String whoResponse = "I'm your OSGi greeter service, welcome!";
    private static final String howRequest = "How are you?";
    private static final String howResponse = "I'm in great form, thanks for asking!";

    // default response message
    private String defaultResponse;

    /**
     * Used by Spring to inject the default response message.
     */
    public void setDefaultResponse(String response) {
        logger.info("Setting the response to '" + response + "'");
        this.defaultResponse = response;
    }

    /**
     * Used by Spring to initialise the bean, and by us to configure any needed resources.
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        logger.info("GreeterImpl initialized.");
        logger.info("Will return response '" + defaultResponse + "'");
    }

    /**
     * Checks the incoming request message and returns an appropriate response message.
     *
     * @see com.redhat.gpe.training.osgi.service.Greeter#sayHello(String message)
     */
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

    /**
     * Used by Spring to shut down the bean, and by us to clean up any open resources.
     *
     * @see org.springframework.beans.factory.DisposableBean()
     */
    public void destroy() throws Exception {
        logger.info("Shutting down...");
    }

}
