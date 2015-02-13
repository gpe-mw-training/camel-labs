package com.redhat.gpe.training.internal;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestfullClient {

    private static final Logger log = LoggerFactory.getLogger(RestfullClient.class);

    private static final String ADD_UPDATE_REQUEST = "/customerservice/customers";
    private static final String GET_DELETE_REQUEST = "/customerservice/customers/124";

    private static final String CUSTOMER_ADD_REQUEST = "{\"Customer\":{\"name\":\"Charles\"}}";
    private static final String CUSTOMER_ADD_RESPONSE = "{\"Customer\":{\"id\":124,\"name\":\"Charles\"}}";

    private static final String CUSTOMER_UPDATE_REQUEST = "{\"Customer\":{\"id\":124,\"name\":\"Pauline\"}}";
    private static final String CUSTOMER_UPDATE_RESPONSE = "{\"Customer\":{\"id\":124,\"name\":\"Pauline\"}}";

    private static final String HOST = "http://localhost";

    // Using the Gateway & CXF Servlet
    private static final String PORT = "9000";
    private static final String WEB_CONTEXT = "/cxf/rest";
    private static final String HOST_PORT = HOST + ":" + PORT + WEB_CONTEXT;

    public static void main(String[] args) throws Exception {
        // Add a new customer
        addCustomer();

        // Get a Customer
        getCustomer();

        // Update a customer
        updateCustomer();

        // Delete a customer
        deleteCustomer();
    }

    public static void addCustomer() throws Exception {
        HttpPost post = new HttpPost(HOST_PORT + ADD_UPDATE_REQUEST);
        StringEntity entity = new StringEntity(CUSTOMER_ADD_REQUEST, "ISO-8859-1");
        post.addHeader("Accept", "application/json");
        entity.setContentType("application/json");
        post.setEntity(entity);
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        log.info("Call the REST Customer service to add a customer");

        try {
            HttpResponse response = httpclient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            log.info("Status code : " + Integer.toString(code));
            log.info("Response    : " + body);
            Assert.assertEquals(200,code);
            Assert.assertEquals(CUSTOMER_ADD_RESPONSE,body);
        } finally {
            httpclient.close();
        }
    }

    public static void getCustomer() throws Exception {
        HttpGet get = new HttpGet(HOST_PORT + GET_DELETE_REQUEST);
        get.addHeader("Accept", "application/json");
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        log.info("Call the REST Customer service to get a customer");

        try {
            HttpResponse response = httpclient.execute(get);
            int code = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            log.info("Status code : " + Integer.toString(code));
            log.info("Response    : " + body);
            Assert.assertEquals(302,code);
            Assert.assertEquals(CUSTOMER_ADD_RESPONSE,body);
        } finally {
            httpclient.close();
        }
    }

    public static void updateCustomer() throws Exception {
        HttpPut put = new HttpPut(HOST_PORT + ADD_UPDATE_REQUEST);
        StringEntity entity = new StringEntity(CUSTOMER_UPDATE_REQUEST, "ISO-8859-1");
        put.addHeader("Accept", "application/json");
        entity.setContentType("application/json");
        put.setEntity(entity);
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        log.info("Call the REST Customer service to update a customer");

        try {
            HttpResponse response = httpclient.execute(put);
            int code = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            log.info("Status code : " + Integer.toString(code));
            log.info("Response    : " + body);
            Assert.assertEquals(200,code);
            Assert.assertEquals(CUSTOMER_UPDATE_RESPONSE,body);
        } finally {
            httpclient.close();
        }
    }

    public static void deleteCustomer() throws Exception {
        HttpDelete del = new HttpDelete(HOST_PORT + GET_DELETE_REQUEST);
        del.addHeader("Accept", "application/json");
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        log.info("Call the REST Customer service to delete a customer");

        try {
            HttpResponse response = httpclient.execute(del);
            int code = response.getStatusLine().getStatusCode();
            Assert.assertEquals(200,code);
            log.info("Status code : " + Integer.toString(code));
        } finally {
            httpclient.close();
        }
    }

}
