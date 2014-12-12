package com.redhat.gpe.training.camel;

import org.apache.camel.builder.RouteBuilder;

public class RecipientRoute extends RouteBuilder {

	public void configure() throws Exception {

        from("timer:recipient?period=5000")
                .setBody().constant("Say Hello for recipient")
                .setHeader("recipients").constant("direct:a;direct:b")
                .recipientList(header("recipients").tokenize(";"));

        from("direct:a").log(">> Recipient A called - ${body}");
        from("direct:b").log(">> Recipient B called - ${body}");

	}

}
