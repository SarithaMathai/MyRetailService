package com.retail.product.details.rest.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.retail.product.details.entity.ProductDetailsItem;
import com.retail.product.details.processor.ProductDetailsProcessor;

@Path("/productdetail/v1")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

public class ProductDetailsRestController {
    @GET
    @Path("/{id}")
    public ProductDetailsItem getProductDtl(@PathParam("id") int id) {
        try {
            ProductDetailsItem prodDtls = new ProductDetailsProcessor().getProductDetails(id);
            return prodDtls;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

}