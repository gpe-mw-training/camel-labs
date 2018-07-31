package com.redhat.gpte.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.spi.ThreadPoolProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainAppSyncOneThread {

    private static Logger logger = LoggerFactory.getLogger(MainAppSyncOneThread.class);

    private Main main;

    public static void main(String[] args) throws Exception {
        MainAppSyncOneThread app = new MainAppSyncOneThread();
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

            // From Timer to Direct
            from("timer://threadDirect?period=5s&delay=1s").routeId("# Timer for Direct #")
               .log(">> Timer Direct thread : ${threadName}")
               .to("direct:thread");

            from("direct:thread")
               .log(">> Direct thread : ${threadName}");

        }
    }


}
