package com.redhat.gpe.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnricherRoute extends RouteBuilder {

    private static SampleAggregator aggregationStrategy = new SampleAggregator();

	public void configure() throws Exception {

	}

}
