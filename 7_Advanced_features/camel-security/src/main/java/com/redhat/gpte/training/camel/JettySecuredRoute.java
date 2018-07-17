package com.redhat.gpte.training.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

public class JettySecuredRoute extends RouteBuilder {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.addRouteBuilder(new JettySecuredRoute());
        main.bind("myAuthHandler",MySecurityHandler.generate());
        main.enableHangupSupport();
        main.start();
    }

    @Override
    public void configure() throws Exception {

        from("jetty://http://localhost:9191/demo?handlers=myAuthHandler")
                .transform(constant("<html><body><p>Bye World</p></body></html>"));


    }
}
