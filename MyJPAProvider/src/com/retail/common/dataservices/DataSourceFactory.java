package com.retail.common.dataservices;

import org.apache.log4j.Logger;

/**
 * A factory that returns the data Source to get delegates that could be supplied with out of the box Windchill or customized by a customer.
 *
 **/
public class DataSourceFactory {
    final static Logger logger = Logger.getLogger(DataSourceFactory.class.getName());


    /**
     * 
     * @param dataSourceType
     * @return
     * @throws Exception
     */
    public DataSource getDataSourceInstance(DataSourceType dataSourceType) throws Exception {
        if (DataSourceType.HSQLDB.equals(dataSourceType)) {           
            return new HSQLDBDataSource();
        } else if (DataSourceType.MONGODB.equals(dataSourceType)) {
            return new MongoDBDataSource();
        } else {
            String errorMessage = "DataSource " + dataSourceType + "is not supported";
            logger.error(errorMessage);
            throw new Exception(errorMessage);
        }
    }


}
