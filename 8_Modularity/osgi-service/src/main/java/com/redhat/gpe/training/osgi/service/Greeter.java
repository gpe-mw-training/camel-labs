package com.redhat.gpe.training.osgi.service;

/**
 * Greeter service interface
 */
public interface Greeter {

    /**
     * This simple operation accepts a request string and returns a response string.
     * How the response is determined is up to the implementing class; all we ask is that you keep it friendly!
     */
    String sayHello(String message);

}
