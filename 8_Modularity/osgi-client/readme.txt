This Exercises shows how a OSGi bundle is created using Maven 2 as well as using Servicemix Properties to configure
settings through the Admin OSGi interface rather than through Spring or Application Properties.

Deployment:

After building the project using "mvn install" please copy the line below into the Karaf Console of Servicemix when
started up in order to to deploy it:

    osgi:install -s mvn:com.fusesource.training/servicemix-exercises-osgi-client/2010.07.12

Then look into the log file and check out what values is used for the "Response" property:

    log:display -n 10

The value should be: "Right back at ya"

After that copy the "etc/greeter.cfg" file to the "<servicemix installation directory>/etc" directory, then use

    osgi:refresh <bundle number from the installation above>

In case you cannot find the bundle number just to a:

    osgi:list | grep "Greeting OSGi Bundle"

and use the number at the left hand side.

Now look for the "Response" property again and check that the value has changed:

    log:display -n 10

The value should be now: "I wandered lonely as a cloud." or whatever value you have set inside the "/etc/greeter.cfg"
file.