package com.retail.product.details.processor;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.retail.common.QueryByIdSpec;
import com.retail.common.TableColumnUtils;
import com.retail.common.dataservices.DBConfiguration;
import com.retail.common.dataservices.DataSource;
import com.retail.common.dataservices.DataSourceFactory;
import com.retail.common.dataservices.DataSourceType;
import com.retail.product.details.entity.ProductDetailsItem;

/**
 * 
 * The back end class that is responsible for processing request
 *
 */
public class ProductDetailsProcessor {
    /**
     * Returns the product based on product id.
     * 
     * @param prodId
     * @return
     * @throws Exception
     */
    public ProductDetailsItem getProductDetails(int prodId) throws Exception {
        DataSource dbInstance = new DataSourceFactory().getDataSourceInstance(DataSourceType.HSQLDB);
        QueryByIdSpec spec = new QueryByIdSpec();
        spec.setSelects(Arrays.asList(TableColumnUtils.PROD_NAME, TableColumnUtils.PROD_CATEGORY));        
        spec.setTableName(DBConfiguration.PRODUCT_DETAIL_TABLE_NAME);
        spec.setIdAttributeName(TableColumnUtils.PROD_ID);
        spec.setIdValue(prodId);
        JSONObject json = dbInstance.executeQuery(spec);
        ProductDetailsItem prod = new ProductDetailsItem();
        addToProductItem(prod, json);
        return prod;
    }
}
