package com.redhat.gpe.training.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.impl.RoutePolicySupport;

public class MyCustomRoutePolicy extends RoutePolicySupport {

    @Override
    public void onExchangeBegin(Route route, Exchange exchange) {
        String body = exchange.getIn().getBody(String.class);
        if ("STOP".equals(body)) {
            try {
                stopConsumer(route.getConsumer());
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

}