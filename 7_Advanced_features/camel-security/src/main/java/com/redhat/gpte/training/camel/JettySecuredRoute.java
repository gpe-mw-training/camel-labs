package com.redhat.gpte.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

public class JettySecuredRoute extends RouteBuilder {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.addRouteBuilder(new JettySecuredRoute());
        // Bind the SecurityHandler with the key myAuthHandler
        main.enableHangupSupport();
        main.start();
    }

    @Override
    public void configure() throws Exception {

    // Add the Jetty Secured Route


    }
}
