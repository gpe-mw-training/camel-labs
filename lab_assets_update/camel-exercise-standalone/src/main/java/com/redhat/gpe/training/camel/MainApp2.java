package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.bean.MyBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp2 {

    private static Logger logger = LoggerFactory.getLogger(MainApp2.class);

    private Main main;

    public static void main(String[] args) throws Exception {
        MainApp2 app = new MainApp2();
        app.boot();
    }

    public void boot() throws Exception {

        // create a Main instance
        main = new Main();

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
                .setBody().constant("Training student")
                .to("direct:logResponse");

            from("direct:logResponse")
                .log(">> This is the first Camel exercise - ${threadName}")
                .bean(MyBean.class,"sayHello");
        }
    }


}
