package com.retail.products.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

import com.retail.common.TableColumnUtils;

@XmlRootElement
public class ProductItem {
    private int productId;
    private String productName;

    private ProductPriceItem productPrice;

    @XmlElement
    public int getProdId() {
        return productId;
    }

    public void setProdId(int prodId) {
        this.productId = prodId;
    }

    @XmlElement
    public String getProdName() {
        return productName;
    }

    public void setProdName(String prodName) {
        this.productName = prodName;
    }

    @XmlElement
    public ProductPriceItem getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPriceItem prodPrice) {
        this.productPrice = prodPrice;
    }

    public ProductItem() {
    }

    public void addToProductItem(JSONObject json) throws JSONException {
        if (json.has(TableColumnUtils.PROD_ID)) {
            setProdId(json.getInt(TableColumnUtils.PROD_ID));
        }
        if (json.has(TableColumnUtils.VALUE) && json.has(TableColumnUtils.CURRENCY_CODE)) {
            ProductPriceItem priceItem = new ProductPriceItem();
            priceItem.setValue(json.getDouble(TableColumnUtils.VALUE));
            priceItem.setCurrencyCode(json.getString(TableColumnUtils.CURRENCY_CODE));
            setProductPrice(priceItem);
        }
        if (json.has(TableColumnUtils.PROD_NAME)) {
            setProdName(json.getString(TableColumnUtils.PROD_NAME));
        }
    }
}