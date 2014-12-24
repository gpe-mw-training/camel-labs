package com.redhat.gpe.training.osgi.impl;

import com.redhat.gpe.training.osgi.HelloWorldSvc;

public class HelloWorldSvcImpl implements HelloWorldSvc {
    public void sayHello() {
        System.out.println( "Hello World!" );
    }
}