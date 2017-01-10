package com.redhat.gpe.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteByCurrencyRouter extends RouteBuilder {

	private static final Logger logger = LoggerFactory.getLogger( RouteByCurrencyRouter.class );

    @EndpointInject(ref = "sourceDirectoryXml")
    Endpoint sourceUri;

    @EndpointInject(ref = "euroSinkUriXml")
    Endpoint euroSinkUri;

    @EndpointInject(ref = "usdSinkUriXml")
    Endpoint usdSinkUri;

    @EndpointInject(ref = "otherSinkUriXml")
    Endpoint otherSinkUri;

	public void configure() throws Exception {	

		from( sourceUri )
            .convertBodyTo( String.class )
            .log( "Message to be handled: ${file:onlyname}, body: ${body}" )

                .choice()
                .when(
                    xpath("/pay:Payments/pay:Currency = 'EUR'")
                        .namespace("pay", "http://www.fusesource.com/training/payment")
                )
                    .log( "This is an Euro XML Payment: ${file:onlyname}" )
                    .to( euroSinkUri )
                .when(
                    xpath("/pay:Payments/pay:Currency = 'USD'")
                        .namespace("pay", "http://www.fusesource.com/training/payment")
                )
                    .log( "This is an USD XML Payment: ${file:onlyname}" )
                    .to( usdSinkUri )
                .otherwise()
                    .log( "This is an Other Currency XML Payment: ${file:onlyname}" )
                    .to( otherSinkUri );

	}

}
