package com.redhat.gpte.training.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.BeforeClass;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseJettyTest extends CamelTestSupport {

    private static volatile int port;
    private static volatile int port2;

    private final AtomicInteger counter = new AtomicInteger(1);

    @BeforeClass
    public static void initPort() throws Exception {
        // start from somewhere in the 23xxx range
        port = AvailablePortFinder.getNextAvailable(23000);
        // find another ports for proxy route test
        port2 = AvailablePortFinder.getNextAvailable(24000);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addComponent("properties", new PropertiesComponent("ref:prop"));
        return context;
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndi = super.createRegistry();

        Properties prop = new Properties();
        prop.setProperty("port", "" + getPort());
        prop.setProperty("port2", "" + getPort2());
        jndi.bind("prop", prop);
        return jndi;
    }

    protected int getNextPort() {
        return AvailablePortFinder.getNextAvailable(port + counter.getAndIncrement());
    }

    protected int getNextPort(int startWithPort) {
        return AvailablePortFinder.getNextAvailable(startWithPort);
    }

    protected static int getPort() {
        return port;
    }

    protected static int getPort2() {
        return port2;
    }


}
