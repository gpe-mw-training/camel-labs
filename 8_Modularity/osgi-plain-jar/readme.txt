This Exercises shows how a OSGi bundle is created using Maven 2 as well as using Servicemix Properties to configure
settings through the Admin OSGi interface rather than through Spring or Application Properties.

Deployment:

After building the project using "mvn install" please copy the line below into the Karaf Console of Servicemix when
started up in order to to deploy it:

    osgi:install -s wrap:mvn:com.fusesource.training/servicemix-exercises-plain-jar/2010.07.12

Now you need to copy the "deploy/plainAndSimple-context.xml" inside the "<servicemix installation directory>/deploy"
directory.

Then look into the log file and check out if you find this message "Initializing Service bean with message" indicating
that the service was deployed:

    log:display -n 10

If you then undeploy with:

    osgi:uninstall <bundle number of the plainAndSimple-context.xml deployment>

then you should find this message "Destroying Service bean with message":

    log:display -n 10
