package com.retail.common.dataservices;

/**
 * This class defines properties that are required for database connection.Ideally this would be a configuration file that is read and load the 
 * definition on initial server startup. 
 * 
 */
public class DBConfiguration {   

    public static final String HSQLDB_DRIVER_CLASS = "org.hsqldb.jdbc.JDBCDriver";

    public static final String HSQLDB_JDBC_URL = "jdbc:hsqldb:hsql://localhost/productdb";

    public static final String HSQLDB_JDBC_USER = "sa";

    public static final String HSQLDB_JDBC_PASSWORD = "";

    public static final String MONGODB_DATABASE = "productdb";

    public static final String MONGODB_HOST = "localhost";

    public static final int MONGODB_PORT = 27017;

    public static final String PRODUCT_TABLE_NAME = "product";
    
    public static final String PRODUCT_DETAIL_TABLE_NAME = "product_detail";
}
