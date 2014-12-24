package com.redhat.gpe.training.jar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * A simple Spring bean with a string property.
 */
public class PlainAndSimple
        implements InitializingBean, DisposableBean {
    // LOG
    private static final Logger LOG = LoggerFactory.getLogger(PlainAndSimple.class);

    // Spring bean property
    private String simpleMessage;

    /**
     * Spring method for setting the above property
     */
    public void setSimpleMessage(String message) {
        this.simpleMessage = message;
    }

    /**
     * Spring method for initializing the bean and its required resources.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Initializing simple bean with message = '" + simpleMessage + "'");
    }

    /**
     * Spring method for shutting down the bean and its open resources.
     */
    @Override
    public void destroy() throws Exception {
        LOG.info("Destroying simple bean with message = '" + simpleMessage + "'");
    }

}
