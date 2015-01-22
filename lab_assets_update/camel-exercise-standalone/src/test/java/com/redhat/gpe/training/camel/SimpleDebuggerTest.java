package com.redhat.gpe.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SimpleDebuggerTest extends CamelTestSupport {

    @Override
    public boolean isUseDebugger() {
        // must enable debugger
        return true;
    }

    @Override
    protected void debugBefore(Exchange exchange, Processor processor,
                               ProcessorDefinition<?> definition, String id, String shortName) {
        // this method is invoked before we are about to enter the given processor
        // from your Java editor you can just add a breakpoint in the code line below
        log.info("Before " + definition + " with body " + exchange.getIn().getBody());
    }

    @Test
    public void testDebugger() throws Exception {
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(1);
        getMockEndpoint("mock:b").expectedMessageCount(1);

        // send a message
        template.sendBody("direct:start", "World");

        // assert mocks
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testTwo() throws Exception {
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(2);
        getMockEndpoint("mock:b").expectedMessageCount(2);

        // send a message
        template.sendBody("direct:start", "World");
        template.sendBody("direct:start", "Camel");

        // assert mocks
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // this is the route we want to debug
                from("direct:start")
                        .to("mock:a")
                        .transform(body().prepend("Hello "))
                        .to("mock:b");
            }
        };
    }
}
