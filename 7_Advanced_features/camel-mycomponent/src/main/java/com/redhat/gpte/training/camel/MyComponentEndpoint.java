package com.redhat.gpte.training.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a MyComponent endpoint.
 */
public class MyComponentEndpoint extends DefaultEndpoint {

    public MyComponentEndpoint() {}

    public MyComponentEndpoint(String uri, MyComponent component) {
        super(uri, component);
    }

    public MyComponentEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new MyComponentProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new MyComponentConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
}
