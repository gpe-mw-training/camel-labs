This Exercises shows how an Camel Aggregator can be used within a Camel Route. This exercise is self-contained
meaning it does not depend on any other component or exercises and therefore can be run standalone or being deployed
within an OSGi container like OSGi.

Deployment:

1) Running it inside an embedded Camel instance:

    mvn clean camel:run

2) Build, Install and Deploy it as OSGi bundle:

    mvn clean install

then please enter this in the Karaf Console:

    osgi:install -s mvn:com.redhat.gpe.training/camel-exercise-aggregator/1.0

3) Install it as feature (need to add the features URL ahead of time, please see the camel-exercise-features project)

    features:install camel-exercise-aggregator

For 2) and 3) the input and output directories are now created under:

    /camel-exercise/aggregator/[in|out]

You can now copy any XML Payments file inside this directory which follows the Payment.xsd schema. In order to
show the full potential please copy both files from the 'src/test/resources/camel/in' directory (VariousUSPayments.xml
as well as VariousEUPayments.xml) at the same time into the 'in' directory.

NOTE:
    The output file concatenates the output so you might need to delete it before dropping new files into the 'in'
    directory for processing.
    That said you should see then that the order of the output is done based on the recipient of the payment (<tns:to>).