This Exercises shows how a OSGi bundle is created using Maven 2 as well as using JBoss Fuse Properties to configure
settings through the Admin OSGi interface rather than through Spring or Application Properties.

Deployment:

After building the project using "mvn install" please copy the line below into the Karaf Console of JBoss Fuse when
started up in order to to deploy it:

    osgi:install -s wrap:mvn:com.redhat.gpe.training/osgi-plain-jar/1.0

Now you need to copy the "deploy/plainAndSimple-context.xml" inside the "<JBoss Fuse installation directory>/deploy"
directory.

Then look into the log file and check out if you find this message "Initializing Service bean with message" indicating
that the service was deployed:

    log:display -n 10

If you then undeploy with:

    osgi:uninstall <bundle number of the plainAndSimple-context.xml deployment>

then you should find this message "Destroying Service bean with message":

    log:display -n 10
