package com.retail.products.rest;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Convenient class to make a rest call and return data in JSON format
 */
public class RestClient {

    /**
     * Helper method that makes rest call and return data in JSON format
     * 
     * @param restAPIUrl
     *            - url to invoke
     * @return
     */
    public static JSONObject executeRestCall(String restAPIUrl) {
        JSONObject object;
        try {

            Client client = Client.create();
            WebResource webResource = client.resource(restAPIUrl);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                System.out.println("Failed HTTP error code = " + response.getStatus());
            }

            String output = response.getEntity(String.class);
            //System.out.println("\n============ getProductDetail Response ============");
            //System.out.println(output);

            object = new JSONObject(output);
            return object;

        } catch (Exception exp) {
            System.out.println("Malformed URL Exception encountered !!!");
            exp.printStackTrace();        
        }
        return null;
    }
}