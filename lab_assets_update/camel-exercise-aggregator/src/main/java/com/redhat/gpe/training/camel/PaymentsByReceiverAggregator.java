package com.redhat.gpe.training.camel;

import java.io.File;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class defines a Camel Route that takes all files from a given directory
 * reads out their XML payments and splits them into single payments. Then it
 * will aggregate the individual payments into a new file ordered by the 
 * receiver of the payment within the given timeout period. 
 */
public class PaymentsByReceiverAggregator extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentsByReceiverAggregator.class);

    @EndpointInject(ref = "sourceEndpoint")
    private Endpoint sourceUri;

    @EndpointInject(ref = "sinkEndpoint")
    private Endpoint sinkUri;

    private int aggregateTimeoutPeriodInSeconds = 5;

    @Override
	public void configure() throws Exception {
        // This route is doing the following:
        // - reading files from the given source directory
        // - splitting them apart into single payments
        // - aggregate them into groups by the receiver value
        // - store them into the given sink directory and file

        // Define the namespace for the Payment XML
        Namespaces ns = new Namespaces("p", "http://www.fusesource.com/training/payment")
                .add("xsd", "http://www.w3.org/2001/XMLSchema");

        from(sourceUri)
                .split()
                .xpath("/p:Payments/p:Payment", ns)
                .log("\nGot separated payment with this content: \n${body}\nwhich is now being handed over to the aggregator\n")
                .convertBodyTo(String.class)
                .aggregate(new BodyAppenderAggregator()) // What should be aggregated
                  .xpath("/p:Payment/p:to", String.class, ns) // How the aggregator should aggregate
                  .completionTimeout(aggregateTimeoutPeriodInSeconds * 1000) // when it should stop
                .log("\nGot aggregated payments with this content: \n${body}\n\nwhich is now being sent to the sink endpoint\n")
                .to(sinkUri);
    }
}
