This Exercises shows how Exceptions and ErrorHandler can augment the management of messages within a Camel Route. This exercise
is self-contained meaning it does not depend on any other component or exercises and therefore can be run standalone or being deployed
within an OSGi container like OSGi.

The exercises contains different camel which are used to simulate exceptions and to show how they are intercepted by Camel depending
if we have use the by default DefaultErrorHandler, DeadLetterChannel and onException.

For EUPayment, a MyFunctional Exception is raised, which is intercepted by the onException() interceptor, the exception is handled
and will not be redelivered. So the exception appears only one time in the log

FOR USPAyment, an Exception is raised which is not intercepted by onException and so the DeadLetterChanned will try to redeliver 2 times the last camel
exchanges before to send the result to a camel route logging the info.

Deployment:

1) Running it inside an embedded Camel instance:

    mvn clean camel:run

2) Build, Install and Deploy it as OSGi bundle:

    mvn clean install

then please enter this in the JBoss Fuse Console:

    osgi:install -s mvn:com.redhat.gpe.training/camel-exercise-exception/1.0

3) Install it as feature (need to add the features URL ahead of time, please see the camel-exercise-features project)

    features:install camel-exercise-exception

For 2) and 3) the input and output directories are now created under:

    /camel-exercise/exception/[in/out]/xml

You can now copy any EUPayments.xml and USPayments.xml file into this directory and have a look to the result

Remark : When you starts the camel plugin camel:run, then the files EUPayments.xml and USPayments.xml must be placed
    in the directory /test-classes/camel/in/xml of target directory


LOG OUTPUT

    EUPayment - MyFunctional excpetion created and handled by onException

Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route1] - Message to be handled: EUPayments.xml, body: <?xml version="1.0" encoding="UTF-8"?>
<tns:Payments xmlns:tns="http://www.fusesource.com/training/payment"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.fusesource.com/training/payment xsd/Payment.xsd ">

    <tns:Currency>EUR</tns:Currency>

    <tns:Payment>
        <tns:from>ade</tns:from>
        <tns:to>jack</tns:to>
        <tns:amount>1000000.0</tns:amount>
    </tns:Payment>
    <tns:Payment>
        <tns:from>jack</tns:from>
        <tns:to>jill</tns:to>
        <tns:amount>20.0</tns:amount>
    </tns:Payment>
    <tns:Payment>
        <tns:from>ade</tns:from>
        <tns:to>jill</tns:to>
        <tns:amount>42.0</tns:amount>
    </tns:Payment>

</tns:Payments>

Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route1] - This is an Euro XML Payment: EUPayments.xml
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route2] - Message will be processed only 1 time.
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [com.redhat.gpe.training.camel.exercise.exception.MyBean] - >>>> Exception created for : EUR, counter = 1
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route4] - %%% MyFunctional Exception handled.

     USPAyment - Exception will be handled by DeadLetterChannel which will repeat twice the last step of the camel route

Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route1] - Message to be handled: USPayments.xml, body: <?xml version="1.0" encoding="UTF-8"?>
<tns:Payments xmlns:tns="http://www.fusesource.com/training/payment"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.fusesource.com/training/payment ../xsd/Payment.xsd ">

    <tns:Currency>USD</tns:Currency>

    <tns:Payment>
        <tns:from>paul</tns:from>
        <tns:to>ade</tns:to>
        <tns:amount>1000000.0</tns:amount>
    </tns:Payment>
    <tns:Payment>
        <tns:from>daan</tns:from>
        <tns:to>jack</tns:to>
        <tns:amount>78.0</tns:amount>
    </tns:Payment>
    <tns:Payment>
        <tns:from>pat</tns:from>
        <tns:to>jill</tns:to>
        <tns:amount>13.0</tns:amount>
    </tns:Payment>

</tns:Payments>

Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route1] - This is an USD XML Payment: USPayments.xml
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route3] - Message will be processed 2 times.
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [com.redhat.gpe.training.camel.exercise.exception.MyBean] - >>>> Exception created for : USD, counter = 1
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [com.redhat.gpe.training.camel.exercise.exception.MyBean] - >>>> Exception created for : USD, counter = 2
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [com.redhat.gpe.training.camel.exercise.exception.MyBean] - >>>> Exception created for : USD, counter = 3
Camel (camel-1) thread #0 - file://./target/test-classes/camel/in/xml INFO [route4] - >>> Info send to DLQ


