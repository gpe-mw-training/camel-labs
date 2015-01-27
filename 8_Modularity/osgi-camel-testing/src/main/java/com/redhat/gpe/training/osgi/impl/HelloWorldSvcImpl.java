package com.redhat.gpe.training.osgi.impl;

import com.redhat.gpe.training.osgi.HelloWorldSvc;

public class HelloWorldSvcImpl implements HelloWorldSvc {
    public String sayHello(String msg) {
        return "Hello to " + msg + " user";
    }
}