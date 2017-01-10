package com.redhat.gpe.training.camel;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.model.language.ELExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.net.ConnectException;

public class SplitIntoSinglePaymentsRouter extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(SplitIntoSinglePaymentsRouter.class);
	
    @EndpointInject(ref = "sourceFileEndpoint")
    private Endpoint sourceFileUri;

    @EndpointInject(ref = "sourceJmsEndpoint")
	private Endpoint sourceJmsUri;

    @EndpointInject(ref = "targetJmsEndpoint")
	private Endpoint targetJmsUri;

    @Override
	public void configure() throws Exception {

        Namespaces ns = new Namespaces("p", "http://www.fusesource.com/training/payment");

        onException(JMSException.class)
           .log("No broker connection is available")
           .handled(true)
           .maximumRedeliveries(0);

        from(sourceFileUri).routeId("jms")
           .to(sourceJmsUri);
    	
		from(sourceJmsUri).routeId("split")
            .log("This payments will be split : ${body}")
            .split().xpath("/p:Payments/p:Payment",ns)
              .setHeader("To").xpath("p:Payment/p:to/text()", ns)
              .setHeader("From").xpath("p:Payment/p:from/text()", ns)
            .log("Content split : ${header.CamelSplitIndex}++ / ${header.CamelSplitSize} (Complete : ${header.CamelSplitComplete}) for Payment to : ${header.To}, from ${header.From} / ${header.CamelSplit}")
			.to(targetJmsUri);

        LOG.info("Configured Splitter Route: Source URI: '" + sourceJmsUri + "', Target URI: '" + targetJmsUri + "'");
	}

}
