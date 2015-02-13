package com.redhat.gpe.training.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A HelloBean containing a string property.
 */
public class HelloBean {

    // LOG
    private static final Logger LOG = LoggerFactory.getLogger(HelloBean.class);

    // Message Property
    private String message;

    /**
     * Method for setting the above property
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method for initializing the bean and its required resources.
     */
    public void init() {
        LOG.info("Initializing the hello bean with message = " + message);
    }

    /**
     * Method for destroying down the bean and its open resources.
     */
    public void destroy() {
        LOG.info("Destroying the hello bean with message = " + message);
    }


}
