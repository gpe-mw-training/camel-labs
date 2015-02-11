package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.model.Customer;
import org.apache.camel.Exchange;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerServiceBean {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceBean.class);
    private final AtomicLong currentId = new AtomicLong(123L);
    private Map<Long, Customer> customers = new HashMap<Long, Customer>();

    public CustomerServiceBean() {
        init();
    }

    final void init() {
        Customer c = new Customer();
        c.setName("Charles");
        c.setId(123);
        customers.put(c.getId(), c);
    }

    public Response getCustomer(Exchange exchange) throws Exception {
        Response response;
        String content = "";

        MessageContentsList list = (MessageContentsList) exchange.getIn().getBody();
        content = (String) list.get(0);

        LOG.info("----invoking getCustomer, Customer id is: " + content);

        long idNumber = Long.parseLong(content);
        Customer c = customers.get(idNumber);

        if (c == null) {
            System.out.println("Customer is null: " + (c == null));
            response = Response.status(Response.Status.NOT_FOUND).entity("<error>Could not find customer</error>").build();
        } else {
            response = Response.status(Response.Status.FOUND).entity(c).build();
        }

        return response;
    }

    public Response updateCustomer(Customer customer) {
        Response r;
        Customer c = customers.get(customer.getId());
        if (c != null) {
            customers.put(customer.getId(), customer);
            r = Response.ok(customer).build();
        } else {
            r = Response.status(406).entity("Cannot find the customer!").build();
        }

        return r;
    }

    public Response addCustomer(Customer customer) {
        customer.setId(currentId.incrementAndGet());

        customers.put(customer.getId(), customer);

        return Response.ok(customer).build();
    }

    public Response deleteCustomer(Exchange exchange) {

        Response response;
        String content = "";

        MessageContentsList list = (MessageContentsList) exchange.getIn().getBody();
        content = (String) list.get(0);

        LOG.info("----invoking deleteCustomer, Customer id is: " + content);

        long idNumber = Long.parseLong(content);
        Customer c = customers.get(idNumber);

        if (c == null) {
            System.out.println("Customer not found : " + (c == null));
            response = Response.notModified().build();
        } else {
            System.out.println("Customer deleted");
            response = Response.ok().build();
        }

        if (idNumber == currentId.get()) {
            currentId.decrementAndGet();
        }
        return response;
    }

}
