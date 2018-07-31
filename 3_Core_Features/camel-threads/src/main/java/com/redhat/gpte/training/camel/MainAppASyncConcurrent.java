package com.redhat.gpte.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainAppASyncConcurrent {

    private static Logger logger = LoggerFactory.getLogger(MainAppASyncConcurrent.class);

    private Main main;

    public static void main(String[] args) throws Exception {
        MainAppASyncConcurrent app = new MainAppASyncConcurrent();
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

            // From Timer to SEDA + Concurrent
            from("timer://threadSedaConcurrent?period=5s&delay=1s").routeId("# Timer for SEDA Concurrent #")
                    .log(">> Timer thread : ${threadName}")
                    .multicast().parallelProcessing()
                    //.to("seda:thread-concurrent?concurrentConsumers=3","seda:thread-concurrent?concurrentConsumers=3","seda:thread-concurrent?concurrentConsumers=3");
                    .to("seda:thread-concurrent","seda:thread-concurrent","seda:thread-concurrent");

            from("seda:thread-concurrent?concurrentConsumers=3")
                    .log(">> Seda concurrent thread : ${threadName}");

        }
    }


}
