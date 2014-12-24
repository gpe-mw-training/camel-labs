package com.redhat.training.pojo;

import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.camel.Body;

public class GreetClient {

    public Greeter getGreeterService() {
        return greeterService;
    }

    public void setGreeterService(Greeter greeterService) {
        this.greeterService = greeterService;
    }

    Greeter greeterService;

    public String sayHello(@Body String message) {
        return greeterService.sayHello(message);
    }

}
