package com.redhat.gpte.training.camel;

import com.redhat.gpte.training.camel.model.Student;
import com.redhat.gpte.training.camel.MyBean;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FromCSVFileToBindyDataFormat extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(FromCSVFileToBindyDataFormat.class);
    private static int count = 0;

    @EndpointInject(ref = "csvDirectory")
    Endpoint sourceUri;

    public void configure() throws Exception {

        BindyCsvDataFormat df = new BindyCsvDataFormat(Student.class);

        from(sourceUri).routeId("dataformat")
          .split(body(String.class).tokenize("\n"))
          .log(">> Student : ${body}")
          .unmarshal(df)
          .beanRef("myBean")
          .marshal(df)
          .log(">> Student Registered : ${body}");
    }

}
