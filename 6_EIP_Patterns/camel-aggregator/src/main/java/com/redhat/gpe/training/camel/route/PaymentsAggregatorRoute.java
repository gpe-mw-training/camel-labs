package com.redhat.gpe.training.camel.route;

import com.redhat.gpe.training.camel.BodyAppenderAggregator;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;

/**
 * This class defines a Camel Route that takes all files from a given directory
 * reads out their XML payments and splits them into single payments. Then it
 * will aggregate the individual payments into a new file ordered by the 
 * receiver of the payment within the given timeout period. 
 */
public class PaymentsAggregatorRoute extends RouteBuilder {

    @EndpointInject(ref = "sourceEndpoint")
    private Endpoint sourceUri;

    @EndpointInject(ref = "destinationEndpoint")
    private Endpoint destinationUri;

    private int aggregateTimeoutPeriodInSeconds = 5;

    @Override
	public void configure() throws Exception {

        // Define the namespace for the Payment XML
        Namespaces ns = new Namespaces("p", "http://training.gpe.redhat.com/payment")
                .add("xsd", "http://www.w3.org/2001/XMLSchema");

        from(sourceUri).to("ADD_SPLITTER_AND_AGGREGATOR").to(destinationUri);
    }
}
