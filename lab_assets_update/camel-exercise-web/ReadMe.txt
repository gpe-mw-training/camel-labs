Camel Router WAR Project
========================

To compile the project

mvn install

You can then run the project by dropping the WAR into your 
favorite web container or just run

mvn jetty:run

to start up and deploy to Jetty.


# Deploy Hawtio & Camel Exercise

  features:install war
  osgi:install -s webbundle:file:camel-exercise-web-1.0.war
  features:addurl mvn:io.hawt/hawtio-karaf/1.3-SNAPSHOT/xml/features
  features:install hawtio
    
