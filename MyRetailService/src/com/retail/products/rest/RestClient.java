package com.retail.products.rest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Convenient class to make a rest call and return data in JSON format
 */
public class RestClient {
	final static Logger logger = Logger.getLogger(RestClient.class.getName());

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
				logger.error("Failed HTTP error code = " + response.getStatus());
			}

			String output = response.getEntity(String.class);

			object = new JSONObject(output);
			return object;

		} catch (Exception exp) {
			logger.error("Malformed URL Exception encountered !!!",exp);
		}
		return null;
	}
}
