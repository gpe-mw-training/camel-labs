package com.redhat.gpte.training.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://exercise?delay=2000&period=10s")
                .log(LoggingLevel.INFO, ">> This is a Camel exercise covering expression languages")

                .setBody()
                      .simple("${type:com.redhat.gpte.training.camel.Subtraction}")

                .setHeader("value1")
                     .simple("${body.getValue1}")
                .setHeader("value2")
                     .simple("${body.getValue2}")

                .setHeader("subtraction")
                     .simple("${body.subtraction()}")

                // Log addition result
                .log(">> ${header.value1} - ${header.value2} = ${header.subtraction}") ;
    }

}
