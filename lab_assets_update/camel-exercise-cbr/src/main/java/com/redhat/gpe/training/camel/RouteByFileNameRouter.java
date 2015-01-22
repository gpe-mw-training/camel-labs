package com.redhat.gpe.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteByFileNameRouter
    extends RouteBuilder
{

	private static Logger logger = LoggerFactory.getLogger( RouteByFileNameRouter.class );

    @EndpointInject(ref = "sourceDirectoryCsv")
    Endpoint sourceUri;

    @EndpointInject(ref = "euroSinkUriCsv")
    Endpoint euroSinkUri;

    @EndpointInject(ref = "usdSinkUriCsv")
    Endpoint usdSinkUri;

    @EndpointInject(ref = "otherSinkUriCsv")
    Endpoint otherSinkUri;

	@Override
	public void configure() throws Exception {

		from( sourceUri )
			.convertBodyTo( String.class )
            // An alternative way to print out logging statements is to hand it to the log component through an URI
			.to( "log:com.redhat.gpe.training.camel?level=DEBUG&showHeaders=true" )
			.choice()
				.when()
                    .simple( "${file:onlyname} == 'EUPayments.txt'" )
                        .log( "This is an Euro Payment: ${file:onlyname}" )
	    				.to( euroSinkUri )
				.when()
                    .simple( "${file:onlyname} == 'USPayments.txt'" )
                        .log( "This is an USD Payment: ${file:onlyname}" )
	    				.to( usdSinkUri )
				.otherwise()
                    .log( "This is an Other Currency Payment: ${file:onlyname}" )
					.to( otherSinkUri );
		
		logger.debug("Starting listening for files in directory '" + sourceUri + "'");
	}

}
