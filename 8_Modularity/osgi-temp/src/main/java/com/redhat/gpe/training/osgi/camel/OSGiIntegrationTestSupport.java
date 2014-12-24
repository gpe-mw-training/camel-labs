package com.redhat.gpe.training.osgi.camel;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

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
import static org.ops4j.pax.exam.OptionUtils.combine;

public class OSGiIntegrationTestSupport extends CamelTestSupport {
    protected static final Logger LOG = LoggerFactory.getLogger(OSGiIntegrationTestSupport.class);
    protected static final AtomicInteger COUNTER = new AtomicInteger();
    protected static String workDir = "target/paxrunner/";
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

    public static MavenArtifactProvisionOption getJUnitBundle() {
        MavenArtifactProvisionOption mavenOption = mavenBundle().groupId("org.apache.servicemix.bundles")
                .artifactId("org.apache.servicemix.bundles.junit");
        mavenOption.versionAsInProject().start(true).startLevel(10);
        return mavenOption;
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

    public static UrlReference getKarafFeatureUrl() {
        String karafVersion = System.getProperty("karafVersion");
        LOG.info("*** The karaf version is " + karafVersion + " ***");

        String type = "xml/features";
        return mavenBundle().groupId("org.apache.karaf.assemblies.features").
                artifactId("standard").version(karafVersion).type(type);
    }

    public static UrlReference getKarafEnterpriseFeatureUrl() {
        String karafVersion = System.getProperty("karafVersion");
        LOG.info("*** The karaf version is " + karafVersion + " ***");

        String type = "xml/features";
        return mavenBundle().groupId("org.apache.karaf.assemblies.features").
                artifactId("enterprise").version(karafVersion).type(type);
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
    public static Option scanFeatures(UrlReference ref, String ... features) {
        return KarafDistributionOption.features(ref, features);
    }
    public static Option scanFeatures(String ref, String ... features) {
        return KarafDistributionOption.features(ref, features);
    }
    public static Option felix() {
        return KarafDistributionOption.editConfigurationFileExtend("etc/config.properties",
                "karaf.framework",
                "felix");
    }
    public static Option equinox() {
        return KarafDistributionOption.editConfigurationFileExtend("etc/config.properties",
                "karaf.framework",
                "equinox");
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
        Option[] options =
                // Set the karaf environment with some customer configuration
                new Option[] {
                        KarafDistributionOption.karafDistributionConfiguration()
                                .frameworkUrl(maven().groupId("org.apache.karaf")
                                        .artifactId("apache-karaf").type("tar.gz").versionAsInProject())
                                .karafVersion(getKarafVersion())
                                .name("Apache Karaf")
                                .useDeployFolder(false)
                                .unpackDirectory(new File("target/paxexam/unpack/")),

                        KarafDistributionOption.replaceConfigurationFile("etc/custom.properties", new File("src/test/resources/org/apache/camel/itest/karaf/custom.properties")),
                        KarafDistributionOption.replaceConfigurationFile("etc/org.ops4j.pax.url.mvn.cfg", new File("src/test/resources/org/apache/camel/itest/karaf/org.ops4j.pax.url.mvn.cfg")),

                        //Grab JUnit and put it very early in the startup to make sure any bundles that are loaded
                        //will use the same version/bundle
                        getJUnitBundle(),
                        // we need INFO logging otherwise we cannot see what happens
                        new LogLevelOption(LogLevelOption.LogLevel.INFO),
                        // install the cxf jaxb spec as the karaf doesn't provide it by default
                        scanFeatures(getCamelKarafFeatureUrl(), "cxf-jaxb", "camel-core", "camel-spring", "camel-test")};

        return options;
    }

    @Configuration
    public static Option[] configure() throws Exception {
        Option[] options = combine(
                getDefaultCamelKarafOptions()
        );

        return options;
    }

}
