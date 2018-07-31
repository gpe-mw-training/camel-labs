package com.redhat.gpte.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

/**
 * The MyComponent consumer.
 */
public class MyComponentConsumer extends ScheduledPollConsumer {

    private final MyComponentEndpoint endpoint;

    public MyComponentConsumer(MyComponentEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected int poll() throws Exception {
    	Exchange exchange = endpoint.createExchange();
        /* Set exchange body with a message */

        try {
            // send message to next processor in the route
            getProcessor().process(exchange);
            return 1; // number of messages polled
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
    }
}
