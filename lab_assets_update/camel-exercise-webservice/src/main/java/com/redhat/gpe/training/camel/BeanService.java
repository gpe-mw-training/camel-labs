package com.redhat.gpe.training.camel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.Body;
import com.redhat.gpe.training.Customer;
import com.redhat.gpe.training.CustomerType;
import com.redhat.gpe.training.GetAllCustomersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanService {

	private String[] names = {"Bob", "Mary", "Peter"};
	
    private static Logger log = LoggerFactory.getLogger(BeanService.class);
    static List<Customer> customers = new ArrayList<Customer>();
    Random randomGenerator = new Random();

    public void generateCustomers() {

    		for (int i=0; i < 3; i++) {
    			String firstName = names[i];
    			generateCustomer(firstName);
    		}
    }
    
    protected void generateCustomer(String firstName) {
        Customer customer = new Customer();
        customer.setName(firstName + " Fuse");
        customer.setNumOrders(randomGenerator.nextInt(100));
        customer.setRevenue(randomGenerator.nextInt(10000));
        customer.setType(CustomerType.BUSINESS);
        customer.setTest(BigDecimal.valueOf(100.00));
        customer.getAddress().add("FuseSource Office");
        customers.add(customer);
    }

    public GetAllCustomersResponse getCustomers(@Body String name) {

        GetAllCustomersResponse response = new GetAllCustomersResponse();
        response.getReturn().addAll(customers);
        return response;
    }

}
