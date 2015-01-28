package com.redhat.gpe.training.osgi.test;

import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;

import static org.ops4j.pax.exam.OptionUtils.combine;

@RunWith(PaxExam.class)
public class BindyDataFormatCsvTest extends OSGiIntegrationTestSupport {

    private static final String FIXED_DATA = "Joe,Smith,Developer,75000,10012009" + "\n"
            + "Jane,Doe,Architect,80000,01152008" + "\n"
            + "Jon,Anderson,Manager,85000,03182007" + "\n";

    @Test
    public void testMarshal() throws Exception {
        List<Employee> employees = getEmployees();

        MockEndpoint mock = getMockEndpoint("mock:bindy-marshal");
        mock.expectedBodiesReceived(FIXED_DATA);

        template.sendBody("direct:marshal", employees);

        mock.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                DataFormat format = new BindyCsvDataFormat(Employee.class);

                from("direct:unmarshal")
                        .unmarshal(format)
                        .split(simple("body"))
                        .to("mock:bindy-unmarshal");

                from("direct:marshal")
                        .marshal(format)
                        .to("mock:bindy-marshal");
            }
        };
    }

    private List<Employee> getEmployees() throws ParseException {
        List<Employee> employees = new ArrayList<>();
        Employee one = new Employee();
        one.setFirstName("Joe");
        one.setLastName("Smith");
        one.setTitle("Developer");
        one.setSalary(75000);
        one.setHireDate(new SimpleDateFormat("MMddyyyy").parse("10012009"));
        employees.add(one);

        Employee two = new Employee();
        two.setFirstName("Jane");
        two.setLastName("Doe");
        two.setTitle("Architect");
        two.setSalary(80000);
        two.setHireDate(new SimpleDateFormat("MMddyyyy").parse("01152008"));
        employees.add(two);

        Employee three = new Employee();
        three.setFirstName("Jon");
        three.setLastName("Anderson");
        three.setTitle("Manager");
        three.setSalary(85000);
        three.setHireDate(new SimpleDateFormat("MMddyyyy").parse("03182007"));
        employees.add(three);
        return employees;
    }

    @Configuration
    public static Option[] configure() {
        Option[] options = combine(
                // Setup a Karaf Container with default options required by Apache Camel
                getDefaultCamelKarafOptions(),
                // Install camel-bindy module
                loadCamelFeatures("camel-bindy"));

        return options;
    }
}