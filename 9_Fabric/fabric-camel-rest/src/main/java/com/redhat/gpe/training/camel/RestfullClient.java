package com.redhat.gpe.training.camel;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RestfullClient {

    public static void main(String[] args) throws Exception {

        // Get a Customer
        getCustomer();

        // Post
        postCustomer();

    }

    public static void postCustomer() throws Exception {
        HttpPost post = new HttpPost("http://localhost:9090/rest/customerservice/customers");
        StringEntity entity = new StringEntity("{\"id\":\"222\",\"name\":\"charles\"}", "ISO-8859-1");
        entity.setContentType("application/json");
        post.setEntity(entity);
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        try {
            HttpResponse response = httpclient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            System.out.println("Status code : " + Integer.toString(code));
            System.out.println("Response : " + body);
        } finally {
            httpclient.close();
        }
    }

    public static void getCustomer() throws Exception {

        /*
            HTTPie req : http GET http://localhost:9090/rest/customerservice/customers/123
            curl req : curl http://localhost:9090/rest/customerservice/customers/123
         */

        HttpGet get = new HttpGet("http://localhost:9090/rest/customerservice/customers/123");
        // DOES NOT WORK
        // get.addHeader("Accept" , "application/json");
        get.addHeader("content-type" , "application/json");
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        try {
            HttpResponse response = httpclient.execute(get);
            int code = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            System.out.println("Status code : " + Integer.toString(code));
            System.out.println("Response : " + body);
        } finally {
            httpclient.close();
        }
    }

}
