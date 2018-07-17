package com.redhat.gpte.training.camel;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link MyComponentEndpoint}.
 */
public class MyComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new MyComponentEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
