Camel Exercise Standalone
=========================

Goal : Create a Camel Java standalone project
---------------------------------------------

A. Using Java Main

Steps to do :

    Dependency : camel-core

    1) Create a Java Main Class (MainApp)
    2) Add a DefaultCamelContext
    3) Register a camel Route (routeBuilder)
    4) Run locally the camel route

    Run the project : mvn exec:java -Dexec.mainClass="com.redhat.gpe.training.camel.MainApp1"


B. Using Camel Main

Steps to do :

    Dependency : camel-core

    1) Create a Camel Main Class (http://camel.apache.org/running-camel-standalone-and-have-it-keep-running.html)
    2) Register a camel Route (routeBuilder)
    3) Run locally the camel route
    4) Exit the route using ctrl-c

    Run the project : mvn exec:java -Dexec.mainClass="com.redhat.gpe.training.camel.MainApp2"
