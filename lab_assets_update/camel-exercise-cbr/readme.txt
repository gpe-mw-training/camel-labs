This Exercises shows how an Camel Content Based Router can be used within a Camel Route. This exercise is self-contained
meaning it does not depend on any other component or exercises and therefore can be run standalone or being deployed
within an OSGi container like OSGi. Compare to the camel-exercise-cbr exercise, we use @EndpointInject annotations
to define the camel endpoints instead of using afterPropertiesSet() method of the class org.springframework.beans.factory.InitializingBean.
The code is more readable and uri of the endpoint can be defined in a property file loaded by spring using
PropertyPlaceHolder mechanism.

Deployment:

1) Running it inside an embedded Camel instance:

    mvn clean camel:run

2) Build, Install and Deploy it as OSGi bundle:

    mvn clean install

then please enter this in the Karaf Console:

    osgi:install -s mvn:com.redhat.gpe.training/camel-exercise-cbr-endpoints/1.0

3) Install it as feature (need to add the features URL ahead of time, please see the camel-exercise-features project)

    features:install camel-exercise-cbr-encpoints

For 2) and 3) the input and output directories are now created under for ServiceMix:

    /camel-exercise/cbr/[in|out]/[csv|xml]

    and

    /target/test-classes/camel-exercise/cbr/[in|out]/[csv|xml]

    for camel:run

You can now copy any XML Payments file into this directory which follows the Payment.xsd schema or the
CSV payment structure.