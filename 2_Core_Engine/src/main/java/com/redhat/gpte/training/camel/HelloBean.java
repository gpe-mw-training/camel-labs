package com.redhat.gpte.training.camel;

import org.apache.camel.Exchange;

public class HelloBean {
    /*
     * Add a method to say Hello
     * Use as input the parameter the body received and concatenate it with "Hello World ! " text
     */
	public String sayHello(Exchange exchange) {
		String body = (String)exchange.getIn().getBody();
		return "Hello world ! " + body;
	}
}
