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
          .log(">> Exception should be thrown as the typeconverter strategy from ArrayList to Vector is not defined.")
          .log(">> Error : ${exception}")
          .handled(true)
          .to("direct:continue");

        from("direct:typeconverter")
           /*
            * We will use an unknown typeConverter strategy
            * From Array.class to Vector.class
            * Camel will raise an exception
            */
           .convertBodyTo(Vector.class)
           .log("We will convert the Object to a Vector");
            
        // .convertBodyTo(Collection.class)
        // .log("We will convert the Object to a Collection");

        from("direct:continue")
           .log(">> We will register the strategy to convert an Array to a Vector")
           .process(new Processor() {
               @Override
               public void process(Exchange exchange) throws Exception {
                   CamelContext context = exchange.getContext();
                   context.getTypeConverterRegistry().addTypeConverter(Vector.class, MyArray.class, new ArrayConverter());
               }
           })
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
