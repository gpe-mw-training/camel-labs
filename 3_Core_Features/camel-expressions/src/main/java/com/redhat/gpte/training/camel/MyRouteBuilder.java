package com.redhat.gpte.training.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRouteBuilder extends RouteBuilder {

//    private static Logger logger = LoggerFactory.getLogger(MyRouteBuilder.class);
    public Calculation calculation;

    public MyRouteBuilder() {
        calculation = new Calculation();
    }

    @Override
    public void configure() throws Exception {

        from("timer://exercise?delay=2000&period=10s")
                .log(LoggingLevel.INFO, ">> This is a Camel exercise covering expression languages")

                // Add an object to the body
                // using a constant expression
                .setBody()
                     .constant(calculation)

                .setHeader("value1")
                     .simple("${body.getValue1}")
                .setHeader("value2")
                     .simple("${body.getValue2}")

                 // Set different Headers
                 // using ognl & simple expressions
                .setHeader("addition")
                     .ognl("request.body.addition()")

                // Log addition result
                .log(">> ${header.value1} + ${header.value2} = ${header.addition}") ;

    }


    public class Calculation {

        private int value1 = 10;
        private int value2 = 20;

        public int addition() {
            return value1 + value2;
        }

        public int getValue1() {
            return value1;
        }

        public void setValue1(int value1) {
            this.value1 = value1;
        }

        public int getValue2() {
            return value2;
        }

        public void setValue2(int value2) {
            this.value2 = value2;
        }

    }
}
