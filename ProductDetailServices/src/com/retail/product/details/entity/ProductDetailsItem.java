package com.retail.product.details.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//import io.swagger.annotations.ApiModel;

//@ApiModel(value = "ProductDetailsItem", description = "Represents a product item with id, name and category.")
@XmlRootElement
@XmlType
public class ProductDetailsItem {
    private int prodId;

    private String prodName;

    private String prodCategory;

    @XmlElement
    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    @XmlElement
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @XmlElement
    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public ProductDetailsItem() {

    }
}