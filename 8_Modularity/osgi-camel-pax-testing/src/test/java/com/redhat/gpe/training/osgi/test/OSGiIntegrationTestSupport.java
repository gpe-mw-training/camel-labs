package com.redhat.gpe.training.osgi.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.osgi.CamelContextFactory;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.karaf.options.KarafDistributionOption;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.UrlReference;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

public class OSGiIntegrationTestSupport extends CamelTestSupport {
    protected static final Logger LOG = LoggerFactory.getLogger(OSGiIntegrationTestSupport.class);

    @Inject
    protected BundleContext bundleContext;

    protected Bundle getInstalledBundle(String symbolicName) {
        for (Bundle b : bundleContext.getBundles()) {
            if (b.getSymbolicName().equals(symbolicName)) {
                return b;
            }
        }
        for (Bundle b : bundleContext.getBundles()) {
            LOG.warn("Bundle: " + b.getSymbolicName());
        }
        throw new RuntimeException("Bundle " + symbolicName + " does not exist");
    }

    static String basedir;

    static {
        try {
            File location = new File(OSGiIntegrationTestSupport.class.getProtectionDomain().getCodeSource().getLocation().getFile());
            basedir = new File(location, "../..").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        LOG.info("Get the bundleContext is " + bundleContext);
        LOG.info("Application installed as bundle id: " + bundleContext.getBundle().getBundleId());

        setThreadContextClassLoader();

        CamelContextFactory factory = new CamelContextFactory();
        factory.setBundleContext(bundleContext);
        factory.setRegistry(createRegistry());
        return factory.createContext();
    }

    protected void setThreadContextClassLoader() {
        // set the thread context classloader current bundle classloader
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
    }

    public static UrlReference getCamelKarafFeatureUrl() {
        return getCamelKarafFeatureUrl(null);
    }

    public static UrlReference getCamelKarafFeatureUrl(String version) {

        String type = "xml/features";
        MavenArtifactProvisionOption mavenOption = mavenBundle().groupId("org.apache.camel.karaf").artifactId("apache-camel");
        if (version == null) {
            return mavenOption.versionAsInProject().type(type);
        } else {
            return mavenOption.version(version).type(type);
        }
    }

    public static UrlReference getKarafFeatureUrl(String version) {

        String type = "xml/features";
        MavenArtifactProvisionOption mavenOption = mavenBundle().groupId("org.apache.karaf.assemblies.features").artifactId("spring");
        if (version == null) {
            return mavenOption.versionAsInProject().type(type);
        } else {
            return mavenOption.version(version).type(type);
        }
    }

    public static Option loadCamelFeatures(String... features) {
        List<String> result = new ArrayList<String>();
        result.add("cxf-jaxb");
        result.add("camel-core");
        result.add("camel-spring");
        result.add("camel-test");
        for (String feature : features) {
            result.add(feature);
        }
        return scanFeatures(getCamelKarafFeatureUrl(), result.toArray(new String[4 + features.length]));
    }

    public static Option scanFeatures(UrlReference ref, String... features) {
        return KarafDistributionOption.features(ref, features);
    }

    private static String getKarafVersion() {
        InputStream ins = OSGiIntegrationTestSupport.class.getResourceAsStream("/META-INF/maven/dependencies.properties");
        Properties p = new Properties();
        try {
            p.load(ins);
        } catch (Throwable t) {
            //
        }
        String karafVersion = p.getProperty("org.apache.karaf/apache-karaf/version");
        if (karafVersion == null) {
            karafVersion = System.getProperty("karafVersion");
        }
        if (karafVersion == null) {
            // setup the default version of it
            karafVersion = "2.4.0";
        }
        return karafVersion;
    }

    public static Option[] getDefaultCamelKarafOptions() {
        return new Option[]{
                getKarafDistributionOption(),

                keepRuntimeFolder(),
                configureConsole().ignoreLocalConsole(),
                replaceConfigurationFile("etc/org.ops4j.pax.url.mvn.cfg", new File(basedir + "/src/test/resources/etc/org.ops4j.pax.url.mvn.cfg")),
                logLevel(LogLevelOption.LogLevel.INFO),

                // Install Spring (required for Karaf 2.3)
                scanFeatures(getKarafFeatureUrl(null), "spring", "spring-dm"),

                // install the camel & camel-test features
                scanFeatures(getCamelKarafFeatureUrl(), "camel", "camel-test")
        };
    }

    public static Option getKarafDistributionOption() {
        return KarafDistributionOption.karafDistributionConfiguration()
                .frameworkUrl(maven().groupId("org.apache.karaf")
                        .artifactId("apache-karaf").type("tar.gz").versionAsInProject())
                .karafVersion(getKarafVersion())
                .name("Apache Karaf")
                .useDeployFolder(false)
                .unpackDirectory(new File("target/paxexam"));
    }

}