package com.redhat.gpte.training.camel;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {

    private static final Logger logger = LoggerFactory.getLogger(MyBean.class);
    private int counter = 0;

    public void process(Exchange exchange) throws Exception {

        String payment = (String) exchange.getIn().getHeader("Payment");

        if (payment.equals("EUR")) {
           counter++;
           logger.info(">>>> Exception created for : " + payment + ", counter = " + counter);
           throw new MyFunctionalException(">>>> MyFunctionalException created.");
        } else if (payment.equals("USD")) {
           counter++;
           logger.info(">>>> Exception created for : " + payment + ", counter = " + counter);
           throw new Exception("===> Exception created");
        }

    }

}
