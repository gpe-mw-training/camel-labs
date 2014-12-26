package com.redhat.gpe.training.osgi.camel;


import com.redhat.gpe.training.osgi.service.Greeter;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.scr.AbstractCamelRunner;
import org.apache.camel.spi.ComponentResolver;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(label = BootStrap.COMPONENT_LABEL, description = BootStrap.COMPONENT_DESCRIPTION, metatype = true)
@Properties({
        @Property(name = "camelContextId", value = "camel-scr-exercise"),
        @Property(name = "active", value = "true")
})
@References({
        @Reference(name = "camelComponent", referenceInterface = ComponentResolver.class,
                cardinality = ReferenceCardinality.MANDATORY_MULTIPLE,
                policy = ReferencePolicy.DYNAMIC,
                policyOption = ReferencePolicyOption.GREEDY,
                bind = "gotCamelComponent",
                unbind = "lostCamelComponent")
})
public class BootStrap extends AbstractCamelRunner {

    public static final String COMPONENT_LABEL = "bootstrap.CamelScrExercise";
    public static final String COMPONENT_DESCRIPTION = "This is the description for camel-scr-exercise.";

    public static final Logger LOGGER = LoggerFactory.getLogger(BootStrap.class);

    @Reference(referenceInterface = Greeter.class)
    Greeter greeterService;

    @Override
    protected List<RoutesBuilder> getRouteBuilders() {

        LOGGER.info("Register the bean of the Greeter service into the Camel Registry");
        registry.put("greeterService", greeterService);

        LOGGER.info("Load the Apache Camel Routes definition");
        List<RoutesBuilder> routesBuilders = new ArrayList<>();
        routesBuilders.add(new GreeterRoute());

        return routesBuilders;
    }
}
