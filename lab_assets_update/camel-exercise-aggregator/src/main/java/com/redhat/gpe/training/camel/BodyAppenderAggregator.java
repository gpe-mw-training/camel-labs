package com.redhat.gpe.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BodyAppenderAggregator implements AggregationStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(BodyAppenderAggregator.class);

    @Override
    public Exchange aggregate( Exchange oldExchange, Exchange newExchange ) {
        LOG.info("Old: " + (oldExchange == null ? "null" : oldExchange.getIn().getBody()) + ", new: " + newExchange.getIn().getBody());
        if( oldExchange == null ) {
            // The messages coming from a splitter can contain duplicate IDs.  If anything down
            // stream depends on unique IDs, for example a file producer endpoint configured in certain ways,
            // it can be negatively affected by this.  Since this aggregation strategy isn't
            // intended to be used directly in a splitter to recombine all parts, we use a new
            // exchange here to avoid propagating duplicate IDs.
            Exchange exchange = new DefaultExchange(newExchange);
            exchange.getIn().setBody(newExchange.getIn().getBody());
            return exchange;
        } else {
            String newBody =
                    oldExchange.getIn().getBody(String.class)
                            + newExchange.getIn().getBody(String.class);
            oldExchange.getIn().setBody( newBody, String.class );
            return oldExchange;
        }
    }
}