package com.redhat.gpe.training.jar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * A HelloBean with managed by Spring containing a string property.
 */
public class HelloBean implements InitializingBean, DisposableBean {

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
     * Spring method for initializing the bean and its required resources.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Initializing hello bean with message = '" + message + "'");
    }

    /**
     * Spring method for shutting down the bean and its open resources.
     */
    @Override
    public void destroy() throws Exception {
        LOG.info("Destroying hello bean with message = '" + message + "'");
    }

}
