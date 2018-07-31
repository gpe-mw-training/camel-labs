package com.redhat.gpte.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelStandaloneApp {

    private static Logger logger = LoggerFactory.getLogger(CamelStandaloneApp.class);

    public static void main(String[] args) throws Exception {

        // create a Main instance
        Main main = new Main();
    }

    private static class MyRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {

        }
    }


}
