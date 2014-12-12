package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.bean.MyBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelStandaloneApp {

    private static Logger logger = LoggerFactory.getLogger(CamelStandaloneApp.class);

    public static void main(String[] args) throws Exception {

        // create a Main instance
        Main main = new Main();
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        // add routes
        main.addRouteBuilder(new MyRouteBuilder());
        // run until you terminate the JVM
        logger.info("Starting Camel. Use ctrl + c to terminate the JVM.\n");

        main.run();
    }

    private static class MyRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {

            from("timer://exercise?delay=2s&period=10s").routeId("# Timer Exercise #")
                .setBody(constant("Student"))
                .bean(MyBean.class, "sayHello")
                .log(">> a Camel exercise - ${body}");
        }
    }


}
