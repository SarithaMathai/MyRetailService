package com.retail.products.rest.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.retail.products.entity.ProductItem;
import com.retail.products.processor.ProductProcessor;

@Path("/products/v1")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class RetailProductRestController {

    @GET
    @Path("/{id}")
    public ProductItem getProduct(@PathParam("id") int id) {
        try {
            return ProductProcessor.getProduct(id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    @PUT
    public ProductItem updateProduct(ProductItem prod) {
        try {
            return ProductProcessor.updateProduct(prod);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}