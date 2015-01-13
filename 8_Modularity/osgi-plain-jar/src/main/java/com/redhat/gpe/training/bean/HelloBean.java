package com.redhat.gpe.training.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * A HelloBean containing a string property.
 */
public class HelloBean {

    // LOG
    private static final Logger LOG = LoggerFactory.getLogger(HelloBean.class);

    // Spring bean property
    private String message;

    /**
     * Spring method for setting the above property
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method for initializing the bean and its required resources.
     */
    public void init() throws Exception {
        LOG.info("Initializing hello bean with message = '" + message + "'");
    }

    /**
     * Method for shutting down the bean and its open resources.
     */
    public void destroy() throws Exception {
        LOG.info("Destroying hello bean with message = '" + message + "'");
    }

}
