package com.redhat.gpe.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpOperationFailedException;
import org.apache.camel.impl.JndiRegistry;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.util.security.Constraint;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

public class HttpBasicAuthTest extends BaseJettyTest {

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndi = super.createRegistry();
        jndi.bind("myAuthHandler", getSecurityHandler());
        return jndi;
    }

    private SecurityHandler getSecurityHandler() throws IOException {
        /*Constraint constraint = new Constraint(Constraint.__BASIC_AUTH, "user");
        constraint.setAuthenticate(true);

        ConstraintMapping cm = new ConstraintMapping();
        cm.setPathSpec("*//*");
        cm.setConstraint(constraint);

        ConstraintSecurityHandler sh = new ConstraintSecurityHandler();
        sh.setAuthenticator(new BasicAuthenticator());
        sh.setConstraintMappings(Arrays.asList(new ConstraintMapping[]{cm}));

        HashLoginService loginService = new HashLoginService("MyRealm", "src/test/resources/myRealm.properties");
        sh.setLoginService(loginService);
        sh.setConstraintMappings(Arrays.asList(new ConstraintMapping[]{cm}));

        return sh;*/
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
