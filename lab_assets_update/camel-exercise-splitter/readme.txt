This Exercises shows how a Splitter can separate message content into multiple Messages.

1) Run the Application Locally using 'camel:run'

This project can be run just by using:

    mvn clean camel:run

2) Deploy the OSGi bundle Application and then push a Message to the Incoming ActiveMQ Queue:

To deploy it as an OSGi bundle first you need to build and install the project:

    mvn clean install

then please enter this in the Karaf Console:

    features:install camel-activemq
    
    osgi:install -s mvn:com.redhat.gpe.training/camel-exercise-splitter/1.0


For 2) the input directory is created under:

    /camel-exercise/splitter/in


Now we want to push a message to the incoming Queue (Payments.XML)
Copy any XML Payments file that follows the Payment.xsd schema to the 'in' directory.
Sample test files can be found in the directory:  src/test/resources/camel/in

Validate the results by reviewing the IndividualPayments.XML queue attributes with JConsole.

