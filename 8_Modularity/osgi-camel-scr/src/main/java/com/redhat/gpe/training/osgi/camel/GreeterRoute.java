package com.redhat.gpe.training.osgi.camel;

import org.apache.camel.builder.RouteBuilder;

public class GreeterRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:exercise").id("fileToBean")
           .convertBodyTo(String.class)
           .log(">> We will call the service using this message : ${body}")
           .beanRef("greeterService", "sayHello(${body})")
           .log(">> Response received : ${body}");
    }
}
