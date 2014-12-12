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
            return null;
        } else {
            return oldExchange;
        }
    }
}