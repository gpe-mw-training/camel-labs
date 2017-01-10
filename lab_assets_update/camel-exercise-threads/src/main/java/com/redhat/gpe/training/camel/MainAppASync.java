package com.redhat.gpe.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.spi.ThreadPoolProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainAppASync {

    private static Logger logger = LoggerFactory.getLogger(MainAppASync.class);

    private Main main;

    public static void main(String[] args) throws Exception {
        MainAppASync app = new MainAppASync();
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

            // From Timer to SEDA
            from("timer://threadSeda?period=10s&delay=7s").routeId("# Timer for SEDA #")
                .log(">> Timer thread : ${threadName}")
                .to("seda:thread");

            from("seda:thread")
                 .log(">> Seda thread : ${threadName}");

            // From Timer to SEDA + Concurrent
            from("timer://threadSedaConcurrent?period=10s&delay=12s").routeId("# Timer for SEDA Concurrent #")
                    .log(">> Timer thread : ${threadName}")
                    .multicast().parallelProcessing()
                    //.to("seda:thread-concurrent?concurrentConsumers=3","seda:thread-concurrent?concurrentConsumers=3","seda:thread-concurrent?concurrentConsumers=3");
                    .to("seda:thread-concurrent","seda:thread-concurrent","seda:thread-concurrent");

            from("seda:thread-concurrent?concurrentConsumers=3")
                    .log(">> Seda concurrent thread : ${threadName}");

        }
    }


}
