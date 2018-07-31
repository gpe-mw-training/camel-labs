package com.redhat.gpte.training.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MyComponentTest extends CamelTestSupport {
/*
    @Test
    public void testMyComponent() throws Exception {

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        assertMockEndpointsSatisfied();

        List<Exchange> exchanges = mock.getReceivedExchanges();
        Exchange exchange = exchanges.get(0);
        String myHeader = (String) exchange.getIn().getHeader("MyHeader");
        assertEquals("foo", myHeader);

        String result = (String) exchange.getIn().getBody();
        assertEquals("Hello World! What a crazy exercise !", result);

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("mycomponent://foo")
                .to("mycomponent://bar")
                .to("mock:result");
            }
        };
    }
*/    
}
