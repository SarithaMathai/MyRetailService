package com.retail.products.entity;

import javax.xml.bind.annotation.XmlElement;

public class ProductPriceItem {
    private double value;
    private String currencyCode;

    @XmlElement
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @XmlElement
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currency_code) {
        this.currencyCode = currency_code;
    }
}
