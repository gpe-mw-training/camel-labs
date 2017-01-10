package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.bean.MyBean;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.*;
import org.apache.camel.model.language.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MainApp1 {

    private static Logger logger = LoggerFactory.getLogger(MainApp1.class);

    public static void main(String[] args) throws Exception {

        // CamelContext = container where we will register the routes
        DefaultCamelContext camelContext = new DefaultCamelContext();

        // Setup a local Registry to keep Beans
        SimpleRegistry myRegistry = new SimpleRegistry();
        myRegistry.put("myBean", new MyBean());

        // Define RouteDefinition, ExchangePattern
        RouteDefinition rd = new RouteDefinition();
        rd.setExchangePattern(ExchangePattern.InOnly);
        rd.setAutoStartup("true");
        rd.setErrorHandlerBuilder(new DefaultErrorHandlerBuilder());
        rd.setTrace("true");

        // Define our lists (consumer and to, to,to)
        List<FromDefinition> fromDefinitionList = new ArrayList<FromDefinition>();
        List<ProcessorDefinition<?>> processorDefinitions = new ArrayList<ProcessorDefinition<?>>();

        // Register a consumer
        FromDefinition from = new FromDefinition("timer://exercise?delay=1s&period=5s");
        fromDefinitionList.add(from);

        // Add a Log EIP processor
        LogDefinition logDefinition = new LogDefinition(">> This is the first Camel exercise - using Route Definition");
        logDefinition.setLoggingLevel(LoggingLevel.INFO );

        // Add a Body with the message
        SetBodyDefinition myBody = new SetBodyDefinition(new SimpleExpression("Training student"));

        // A Bean
        BeanDefinition myBean = new BeanDefinition("myBean","sayHello");

        // Add the processors to the to list
        processorDefinitions.add(logDefinition);
        processorDefinitions.add(myBody);
        processorDefinitions.add(myBean);

        // Complete Route definition
        rd.setInputs(fromDefinitionList);
        rd.setOutputs(processorDefinitions);

        // Register RouteDefinition & RouteBuilder
        camelContext.addRouteDefinition(rd);
        camelContext.setRegistry(myRegistry);

        // Start the container
        camelContext.start();

        // give it time to realize it has work to do
        Thread.sleep(20000);
    }
}
