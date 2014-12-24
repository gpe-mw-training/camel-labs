package com.redhat.gpe.training.osgi.camel;

import com.redhat.gpe.training.osgi.HelloWorldSvc;
import com.redhat.gpe.training.osgi.impl.HelloWorldSvcImpl;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;
import org.osgi.framework.ServiceReference;

import java.util.Dictionary;
import java.util.Map;

public class BlueprintOSGiServiceTest extends CamelBlueprintTestSupport {

    private HelloWorldSvcImpl helloSvc = new HelloWorldSvcImpl();

    @Override
    protected String getBlueprintDescriptor() {
        return "blueprint-camel-test.xml"; // <1>
    }

    @Override // <2>
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        services.put(HelloWorldSvcImpl.class.getName(), asService(helloSvc, null));
    }

    @Test
    public void testRoute() throws Exception {

        // set mock expectations
        getMockEndpoint("mock:result").expectedMessageCount(1);

        // send a message
        template.sendBody("direct:start", "World");

        // assert mock
        assertMockEndpointsSatisfied(); // <3>

        ServiceReference<?> ref = getBundleContext().getServiceReference(HelloWorldSvcImpl.class);
        Object service = getBundleContext().getService(ref);
        assertSame(helloSvc, service); // <4>
    }

}
