package com.retail.common;

import java.util.List;
import java.util.SortedMap;

/**
 * This class represents a Query and is in terms of is constructed in terms of classes, select statements, and search
 * conditions. As part of the prototype, the queries supported are lookup or update by id. The appropriate DataSource
 * class constructs a SQL query statement as per the database syntax.
 */
public class QueryByIdSpec {
    // name of the table
    private String tableName;

    // List of selectables
    private List<String> selects;

    // Attribute Name of id
    private String idAttributeName;

    // value of id
    private int idValue;

    // Map of field name and value pair for update.
    private SortedMap<String, String> updateValueMap;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getSelects() {
        return selects;
    }

    public void setSelects(List<String> selects) {
        this.selects = selects;
    }

    public String getIdAttributeName() {
        return idAttributeName;
    }

    public void setIdAttributeName(String idColumnName) {
        this.idAttributeName = idColumnName;
    }

    public int getIdValue() {
        return idValue;
    }

    public void setIdValue(int idValue) {
        this.idValue = idValue;
    }

    public SortedMap<String, String> getUpdateValueMap() {
        return updateValueMap;
    }

    public void setUpdateValueMap(SortedMap<String, String> updateValueMap) {
        this.updateValueMap = updateValueMap;
    }
    
    /**
     * Helper method to check if necessary elements are there for constructing a query statement for select
     */
    public boolean hasAllQueryElementsForSelect() {
        if (getTableName() == null || getTableName().isEmpty()
                || getSelects() == null || getSelects().isEmpty()
                || getIdAttributeName() == null || getIdAttributeName().isEmpty()
                || getIdValue() <= 0) {
          
            return false;
        }
        return true;
    }
    
    /**
     * Helper method to check if necessary elements are there for constructing a query statement for update
     */
    public boolean hasAllQueryElementsForUpdate() {
        if (getTableName() == null || getTableName().isEmpty()
                || getUpdateValueMap() == null || getUpdateValueMap().isEmpty()
                || getIdAttributeName() == null || getIdAttributeName().isEmpty()
                || getIdValue() <= 0) {
            return false;
        }
        return true;
    }

}