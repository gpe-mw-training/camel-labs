package com.redhat.training.greet;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Greeter service client
 */
public class GreeterClient implements InitializingBean, DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(GreeterClient.class);

    // OSGi Greeter service reference
    private Greeter serviceReference;

    // default request message
    private String defaultRequest;

    /**
     * Used by Spring to inject the OSGi Greeter service reference.
     */
    public void setService(Greeter serviceReference) {
        this.serviceReference = serviceReference;
        LOG.info("Service endpoint = " + serviceReference);
    }

    /**
     * Used by Spring to inject the default request message.
     */
    public void setDefaultRequest(String defaultRequest) {
        this.defaultRequest = defaultRequest;
        LOG.info("Default Request = " + defaultRequest);
    }

    /**
     * Used by Spring to initialise the bean, and by us to invoke the OSGi Greeter service several times over.
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
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
            String response = serviceReference.sayHello(message);
            LOG.info("Received response : '" + response + "'");
        } catch (Exception ex) {
            LOG.error("Error : " + ex.getMessage());
        }
    }

    /**
     * Used by Spring to shut down the bean, and by us to clean up any open resources.
     *
     * @see org.springframework.beans.factory.DisposableBean
     */
    @Override
    public void destroy() throws Exception {
        LOG.info("Shutting down...");
        LOG.info("Finished.");
    }

}
