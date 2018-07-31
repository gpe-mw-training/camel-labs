package com.redhat.gpte.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteByCurrencyRouter extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(RouteByCurrencyRouter.class);
    private static int count = 0;

    @EndpointInject(ref = "sourceDirectoryXml")
    Endpoint sourceUri;

    @EndpointInject(ref = "moneyUriXml")
    Endpoint moneyUriXml;

    @EndpointInject(ref = "directErrorHandler")
    Endpoint directErrorHandler;

    @EndpointInject(ref = "directErrorHandlerWithException")
    Endpoint directErrorHandlerWithException;

    @EndpointInject(ref = "directDLQError")
    Endpoint directDLQError;


    public void configure() throws Exception {

        // TODO Add Try/catch/Block

        onException(MyFunctionalException.class).routeId("myfunctional-exception")
                .maximumRedeliveries(0)
                .handled(true)
                .log(LoggingLevel.INFO, "%%% MyFunctional Exception handled.");

        from(sourceUri).routeId("cbr")
        .convertBodyTo(String.class)
        .log(LoggingLevel.INFO, "Message to be handled: ${file:onlyname}, body: ${body}")
            .choice()
                .when(
                        xpath("/pay:Payments/pay:Currency = 'EUR'")
                                .namespace("pay", "http://www.fusesource.com/training/payment"))
                    .log(LoggingLevel.INFO, "This is an Euro XML Payment: ${file:onlyname}")
                    .setHeader("Payment").simple("EUR")
                    .to(directErrorHandlerWithException)
                .when(
                    xpath("/pay:Payments/pay:Currency = 'USD'")
                        .namespace("pay", "http://www.fusesource.com/training/payment"))
                    .log(LoggingLevel.INFO,  "This is an USD XML Payment: ${file:onlyname}" )
                    .setHeader("Payment").simple("USD")
                    .to( directErrorHandler )
                .otherwise()
                    .log(LoggingLevel.INFO, "This is an Other Currency XML Payment: ${file:onlyname}" )
                    .to( moneyUriXml );


        from(directErrorHandlerWithException).routeId("direct-error-handler-with-exception")
        // DefaultErroHandler will be used as default
        .log("Message will be processed only 1 time.")
        .beanRef("myBeanErrorException");

        from(directErrorHandler).routeId("direct-error-handler")
        // We use DLD route information after 2 retries
        .errorHandler(deadLetterChannel(directDLQError).maximumRedeliveries(2))
        .log("Message will be processed 2 times.")
        .beanRef("myBeanError");

        // Direct Camel Routes
        from(directDLQError).routeId("DLQ")
        .log(">>> Info send to DLQ");

    }

}

class MyFunctionalException extends Exception {

    public MyFunctionalException(String message) {
        super(message);
    }
}