package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.model.Customer;
import com.redhat.gpe.training.camel.model.Order;
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

        if (list != null && !list.isEmpty()) {
            content = (String) list.get(0);
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity("<error>Request is empty</error>").build();
        }

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
        Customer c = customers.get(customer.getId());
        Response r;
        if (c != null) {
            customers.put(customer.getId(), customer);
            r = Response.ok().build();
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

        if (list != null && !list.isEmpty()) {
            content = (String) list.get(0);
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity("<error>Request is empty</error>").build();
        }

        LOG.info("----invoking deleteCustomer, Customer id is: " + content);

        long idNumber = Long.parseLong(content);
        Customer c = customers.get(idNumber);

        if (c == null) {
            System.out.println("Customer not found : " + (c == null));
            response = Response.notModified().build();
        } else {
            response = Response.ok().build();
        }

        if (idNumber == currentId.get()) {
            currentId.decrementAndGet();
        }
        return response;
    }

    public static Response operationNotFound() {
        return Response.status(Response.Status.BAD_REQUEST).entity("Operation not found - ${header.operationName}").build();
    }
}
