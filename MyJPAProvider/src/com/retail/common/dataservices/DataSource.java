package com.retail.common.dataservices;

import org.json.JSONObject;

import com.retail.common.QueryByIdSpec;

/**
 * Interface that Defines the methods required for database operation
 */
public interface DataSource {

    /**
     * Returns an instance of Datasource.
     * 
     * @param databaseName
     * @return DataSource instance
     */
    public abstract DataSource getDBSourceInstance();

    /**
     * Gets the rows from the table, based on the passed in QueryByIdSpec.
     * 
     * @param spec
     *            - QueryByIdSpec
     * @return JSONObject representing the rows
     */
    public abstract JSONObject executeQuery(QueryByIdSpec spec);

    /**
     * Performs the update operation on table, based on the passed in QueryByIdSpec.
     * 
     * @param spec
     *            - QueryByIdSpec
     * @return JSONObject representing the rows being updated
     */
    public abstract void updateQuery(QueryByIdSpec spec);

}