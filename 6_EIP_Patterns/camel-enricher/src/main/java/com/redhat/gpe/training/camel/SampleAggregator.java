package com.redhat.gpe.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class SampleAggregator implements AggregationStrategy {

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange == null) {
            return oldExchange;
        }
        Object oldBody = oldExchange.getIn().getBody();
        Object newBody = newExchange.getIn().getBody();
        oldExchange.getIn().setBody(oldBody + ":" + newBody);
        return oldExchange;
    }

}
