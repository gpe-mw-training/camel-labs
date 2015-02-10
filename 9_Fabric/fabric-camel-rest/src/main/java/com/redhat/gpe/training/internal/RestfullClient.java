package com.redhat.gpe.training.internal;

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

    /*
      HTTPie request
      echo '{"Customer":{"name":"charles"}}' | http POST http://localhost:9090/rest/customerservice/customers
      echo '{"Customer":{"name":"jeff"}}' | http POST http://localhost:9090/rest/customerservice/customers
      echo '{"Customer":{"name":"chad"}}' | http POST http://localhost:9090/rest/customerservice/customers

      curl request
      curl -i -H "Content-Type: application/json" -X POST -d '{"Customer":{"name":"Charles"}}' http://localhost:9090/rest/customerservice/customers
    */
    public static void postCustomer() throws Exception {
        HttpPost post = new HttpPost("http://localhost:9090/rest/customerservice/customers");
        StringEntity entity = new StringEntity("{\"Customer\":{\"id\":\"222\",\"name\":\"charles\"}}", "ISO-8859-1");
        post.addHeader("Accept", "application/json");
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

    /*

      HTTPie req
      http GET http://localhost:9090/rest/customerservice/customers/123
      http GET http://localhost:9090/rest/customerservice/customers/124
      http GET http://localhost:9090/rest/customerservice/customers/125
      http GET http://localhost:9090/rest/customerservice/customers/126

      curl req
      curl http://localhost:9090/rest/customerservice/customers/123
    */
    public static void getCustomer() throws Exception {

        HttpGet get = new HttpGet("http://localhost:9090/rest/customerservice/customers/123");
        get.addHeader("Accept", "application/json");
        //get.addHeader("content-type" , "application/json");
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
