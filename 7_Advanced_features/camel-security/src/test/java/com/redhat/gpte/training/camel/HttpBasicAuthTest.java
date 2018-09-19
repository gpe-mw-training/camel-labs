package com.redhat.gpte.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.impl.JndiRegistry;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

public class HttpBasicAuthTest extends BaseJettyTest {

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndi = super.createRegistry();
        jndi.bind("myAuthHandler", getSecurityHandler());
        return jndi;
    }

    private SecurityHandler getSecurityHandler() throws IOException {
        ConstraintSecurityHandler sh = MySecurityHandler.generate();
        return sh;
    }

    @Test
    public void testHttpBasicAuth() throws Exception {
        String out = template.requestBody("http://localhost:{{port}}/test?authMethod=Basic&authUsername=donald&authPassword=duck", "Hello World", String.class);
        assertEquals("Bye World", out);
    }

    @Test
    public void testHttpBasicAuthInvalidPassword() throws Exception {
        try {
            template.requestBody("http://localhost:{{port}}/test?authMethod=Basic&authUsername=donald&authPassword=sorry", "Hello World", String.class);
            fail("Should have thrown exception");
        } catch (RuntimeCamelException e) {
            HttpOperationFailedException cause = assertIsInstanceOf(HttpOperationFailedException.class, e.getCause());
            assertEquals(401, cause.getStatusCode());
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jetty://http://localhost:{{port}}/test?handlers=myAuthHandler")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                HttpServletRequest req = exchange.getIn().getBody(HttpServletRequest.class);
                                assertNotNull(req);
                                Principal user = req.getUserPrincipal();
                                assertNotNull(user);
                                assertEquals("donald", user.getName());
                            }
                        })
                        .transform(constant("Bye World"));
            }
        };
    }
}
