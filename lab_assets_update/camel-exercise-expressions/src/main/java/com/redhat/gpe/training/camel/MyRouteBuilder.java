package com.redhat.gpe.training.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(MyRouteBuilder.class);
    public MyBean myBean;

    public MyRouteBuilder() {
        myBean = new MyBean();
    }

    @Override
    public void configure() throws Exception {

        from("timer://exercise?delay=2000&period=10s")
                .log(LoggingLevel.INFO, ">> This is a Camel exercise covering expression languages")

                // Add an object to the body
                // using a constant expression
                .setBody().constant(myBean)

                // Set different Headers
                // using ognl & simple expressions
                .setHeader("addition")
                     .ognl("request.body.addition()")

                .setHeader("val1")
                     .simple("${body.getVal1}")
                .setHeader("val2")
                     .simple("${body.getVal2}")

                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        logger.info(">> Invoked timer at " + new Date());
                    }
                })

                // Log addition result
                .log(">> ${header.val1} + ${header.val2} = ${header.addition}") ;

    }


    public class MyBean {

        private int val1 = 10;
        private int val2 = 20;

        public int addition() {
            return val1 + val2;
        }


        public int getVal1() {
            return val1;
        }

        public void setVal1(int val1) {
            this.val1 = val1;
        }

        public int getVal2() {
            return val2;
        }

        public void setVal2(int val2) {
            this.val2 = val2;
        }

    }
}
