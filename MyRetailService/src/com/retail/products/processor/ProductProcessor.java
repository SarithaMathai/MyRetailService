package com.retail.products.processor;

import java.util.Arrays;
import java.util.TreeMap;

import org.json.JSONObject;

import com.retail.common.QueryByIdSpec;
import com.retail.common.TableColumnUtils;
import com.retail.common.dataservices.DBConfiguration;
import com.retail.common.dataservices.DataSource;
import com.retail.common.dataservices.DataSourceFactory;
import com.retail.common.dataservices.DataSourceType;
import com.retail.products.entity.ProductItem;
import com.retail.products.rest.RestClient;

public class ProductProcessor {
    
    private static final String EXT_REST_URL_BASE = "http://localhost:9080/ProductDetailServices/productdetail/v1/";
    							
    public static JSONObject getProductDetails(int prodId) throws Exception{
        DataSource dbInstance = new DataSourceFactory().getDataSourceInstance(DataSourceType.MONGODB);
        QueryByIdSpec spec = new QueryByIdSpec();
        spec.setSelects(Arrays.asList(TableColumnUtils.VALUE,TableColumnUtils.CURRENCY_CODE));
        spec.setTableName(DBConfiguration.PRODUCT_TABLE_NAME);
        spec.setIdAttributeName(TableColumnUtils.PROD_ID);
        spec.setIdValue(prodId);
        return dbInstance.executeQuery(spec);
    }
    
    
    public static ProductItem getProduct(int prodId) throws Exception{
        ProductItem productItem = new ProductItem();
        productItem.addToProductItem(getProductDetails(prodId));
        String restAPIUrl = EXT_REST_URL_BASE+prodId;                                
        JSONObject json = RestClient.executeRestCall(restAPIUrl);
        productItem.addToProductItem(json);
        return productItem;        
    }


    public static ProductItem updateProduct(ProductItem prod) throws Exception {
    	DataSource dbInstance = new DataSourceFactory().getDataSourceInstance(DataSourceType.MONGODB);
        QueryByIdSpec spec = new QueryByIdSpec();
        TreeMap<String, String> map = new TreeMap<String, String>(); 
        //map.put(TableColumnUtils.CURRENCY_CODE, prod.getProductPrice().getCurrencyCode());
        map.put(TableColumnUtils.VALUE, Double.toString(prod.getProductPrice().getValue()));
        spec.setUpdateValueMap(map);
        spec.setTableName(DBConfiguration.PRODUCT_TABLE_NAME);
        spec.setIdAttributeName(TableColumnUtils.PROD_ID);
        spec.setIdValue(prod.getProdId());
        dbInstance.updateQuery(spec);
        return getProduct(prod.getProdId());
    }
}