package com.redhat.gpte.training.camel;

import com.redhat.gpte.training.camel.bean.MyArray;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class MessageProducer {

    private CamelContext context;

    public MessageProducer() {
    }

    public void sendMessage() {

        // A ProducerTemplate is required to send a message to the direct endpoint
        ProducerTemplate template = context.createProducerTemplate();

        // Create an array
        MyArray myArray = new MyArray();
        myArray.add(0, "Hong");
        myArray.add(1, "Jeff");
        myArray.add(1, "Chad");

        // send the message to the ednpoint
        template.sendBody("direct:typeconverter", myArray);
    }

    public void setContext(CamelContext context) {
        this.context = context;
    }


}
