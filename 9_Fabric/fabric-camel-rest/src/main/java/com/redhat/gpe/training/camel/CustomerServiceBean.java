package com.redhat.gpe.training.camel;

import com.redhat.gpe.training.camel.model.Customer;
import com.redhat.gpe.training.camel.model.Order;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class CustomerServiceBean {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceBean.class);
    private Map<Long, Customer> customers = new HashMap<Long, Customer>();
    private Map<Long, Order> orders = new HashMap<Long, Order>();

    public CustomerServiceBean() {
        init();
    }

    final void init() {
        Customer c = new Customer();
        c.setName("John");
        c.setId(123);
        customers.put(c.getId(), c);

        Order o = new Order();
        o.setDescription("order 223");
        o.setId(223);
        orders.put(o.getId(), o);
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

    public Response updateCustomer(Customer c) throws Exception {
        Response response;
        if (c == null) {
            System.out.println("Customer is null: " + (c == null));
            response = Response.status(Response.Status.NOT_FOUND).entity("<error>Customer received is null</error>").build();
        } else {
            response = Response.status(Response.Status.FOUND).entity(c).build();
        }
        return response;
    }

    public static Response operationNotFound() {
        return Response.status(Response.Status.BAD_REQUEST).entity("Operation not found - ${header.operationName}").build();
    }
}
