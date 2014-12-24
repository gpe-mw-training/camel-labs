package com.redhat.gpe.training.osgi;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;

public class GreetClient {

    public Greeter getGreeterService() {
        return greeterService;
    }

    public void setGreeterService(Greeter greeterService) {
        this.greeterService = greeterService;
    }

    Greeter greeterService;

    public String sayHello1(@Body String message) {
        return greeterService.sayHello(message);
    }

    public String sayHello2(Exchange exchange) {
        String payload = (String)exchange.getIn().getBody();
        return greeterService.sayHello(payload);
    }


}
