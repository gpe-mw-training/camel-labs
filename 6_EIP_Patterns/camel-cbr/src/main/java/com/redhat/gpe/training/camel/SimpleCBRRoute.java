package com.redhat.gpe.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;

public class SimpleCBRRoute extends RouteBuilder {

    // @EndpointInject(ref="sourceDirectoryCsv")
    Endpoint sourceUri;

    // @EndpointInject(ref="euroSinkCsv")
    Endpoint euroSinkUri;

    // @EndpointInject(ref="usdSinkCsv")
    Endpoint usdSinkUri;

    // @EndpointInject(ref="otherSinkCsv")
    Endpoint otherSinkUri;

	@Override
	public void configure() throws Exception {

		from(sourceUri).to("ADD_CBR");
	}

}
