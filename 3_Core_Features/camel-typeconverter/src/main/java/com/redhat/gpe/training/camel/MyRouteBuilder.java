package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.bean.MyArray;
import com.redhat.gpe.training.camel.converter.ArrayConverter;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class MyRouteBuilder extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(MyRouteBuilder.class);

    @Override
    public void configure() throws Exception {

        onException(NoTypeConversionAvailableException.class)
          .log(">> Exception should be throw as the typeconverter strategy is not defined from ArrayList to Vector.")
          .log(">> Error : ${exception}")
          .handled(true)
          .to("direct:continue");

        /*
         * Solution for first exercise
         *

         from("direct:typeconverter")
           .convertBodyTo(Collection.class)
           .log("We will convert the Object to a Collection");

         *
         */

        from("direct:typeconverter")
           /*
            * We will use an unknown typeConverter strategy
            * From Array.class to Vector.class
            * Camel will raise an exception
            */
           .convertBodyTo(Vector.class)
           .log("We will convert the Object to a Vector");

        from("direct:continue")
           .log(">> We will register the strategy to convert an Array to a Vector")
           .process(new Processor() {
               @Override
               public void process(Exchange exchange) throws Exception {
                   // CamelContext context = exchange.getContext();
                   // context.getTypeConverterRegistry().addTypeConverter(Vector.class, MyArray.class, new ArrayConverter());
               }
           })
           /*
            * No error has been Thrown as we have the right strategy
            */
           .convertBodyTo(Vector.class)
           .log(">> Type looks good now")
           .process(new Processor() {
               @Override
               public void process(Exchange exchange) throws Exception {
                   List<String> aList = (List<String>) exchange.getIn().getBody();
                   for (String val : aList) {
                       logger.info("Student : " + val);
                   }
               }
           });

    }

}
