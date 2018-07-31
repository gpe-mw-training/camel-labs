package com.redhat.gpte.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.spi.ThreadPoolProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainAppSyncMultiThreads {

    private static Logger logger = LoggerFactory.getLogger(MainAppSyncMultiThreads.class);

    private Main main;

    public static void main(String[] args) throws Exception {
        MainAppSyncMultiThreads app = new MainAppSyncMultiThreads();
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

            // Create a custom Thread Pool
            ThreadPoolProfile pool = new ThreadPoolProfile("my-pool");
            pool.setKeepAliveTime(20L);
            pool.setMaxPoolSize(5);
            pool.setPoolSize(1);
            pool.setDefaultProfile(true);

            // Register it
            getContext().getExecutorServiceManager().registerThreadPoolProfile(pool);

            // From Timer to Direct & Threads
            from("timer://threadDirect?period=5s&delay=1s").routeId("# Timer for Direct Parallel#")
                .log(">> Timer Direct parallel thread : ${threadName}")
                .multicast()
                    .parallelProcessing()
                    .to("direct:threadparallel", "direct:threadparallel");

            from("direct:threadparallel")
                .threads().executorServiceRef("my-pool")
                .log(">> Direct parallel thread : ${threadName}");

        }
    }


}
