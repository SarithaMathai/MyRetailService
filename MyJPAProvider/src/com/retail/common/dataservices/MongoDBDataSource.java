package com.retail.common.dataservices;

import java.util.SortedMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.retail.common.QueryByIdSpec;
import com.retail.common.TableColumnUtils;
import com.retail.common.TableColumnUtils.DataType;

/**
 * Data Source representing the HSQLDB
 *
 */
public class MongoDBDataSource implements DataSource {
    final static Logger logger = Logger.getLogger(DataSourceFactory.class.getName());

    MongoClient mongo;
    private DB db;
    private static MongoDBDataSource dataSource = null;

    public MongoDBDataSource() {
        // Performs required initialization and acquire a DB connection.
        initialize();
    }

    @Override
    public DataSource getDBSourceInstance() {    
        return null;
    }

    @Override
    public JSONObject executeQuery(QueryByIdSpec spec) {
        JSONObject json = new JSONObject();
        BasicDBObject query = new BasicDBObject();
        query.put(TableColumnUtils.getBeanToDbMap().get(spec.getIdAttributeName()), spec.getIdValue());
        DBCollection table = GetTable(db, spec.getTableName());

        DBCursor cursor = table.find(query);
        while (cursor.hasNext()) {
            JSONObject prodJson = new JSONObject();
            try {
                prodJson = new JSONObject(cursor.next().toString());
            } catch (JSONException e1) {               
                e1.printStackTrace();
            }
            for (String column : spec.getSelects()) {
                String dbField = TableColumnUtils.getBeanToDbMap().get(column);
                DataType dataType = TableColumnUtils.getDataType(dbField);

                try {
                    json.put(spec.getIdAttributeName(), spec.getIdValue());

                    if (DataType.STRING.equals(dataType)) {
                        json.put(column, prodJson.getString(dbField));
                    } else if (DataType.DOUBLE.equals(dataType)) {
                        json.put(column, prodJson.getDouble(dbField));
                    } else if (DataType.INT.equals(dataType)) {
                        json.put(column, prodJson.getInt(dbField));                    
                    }
                } catch (JSONException e) {                  
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    @Override
    public void updateQuery(QueryByIdSpec spec) {
        BasicDBObject query = new BasicDBObject();
        query.put(TableColumnUtils.getBeanToDbMap().get(spec.getIdAttributeName()), spec.getIdValue());
        DBCollection table = GetTable(db, spec.getTableName());

        SortedMap<String, String> updateValueMap = spec.getUpdateValueMap();
        BasicDBObject newDocument = new BasicDBObject();
        for (String column : updateValueMap.keySet()) {
            String dbField = TableColumnUtils.getBeanToDbMap().get(column);
            DataType dataType = TableColumnUtils.getDataType(dbField);
            if (DataType.STRING.equals(dataType)) {
                newDocument.put(dbField, updateValueMap.get(column));
            } else if (DataType.DOUBLE.equals(dataType)) {
                newDocument.put(dbField, Double.parseDouble(updateValueMap.get(column)));

            } else if (DataType.INT.equals(dataType)) {
                newDocument.put(dbField, Integer.parseInt(updateValueMap.get(column)));
            }
        }
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);
        table.update(query, updateObj);
    }

    /**
     * database initialization on creating an instance.
     */
    @SuppressWarnings("deprecation")
    private void initialize() {
        mongo = new MongoClient(DBConfiguration.MONGODB_HOST, DBConfiguration.MONGODB_PORT);
        db = mongo.getDB(DBConfiguration.MONGODB_DATABASE);
    }

    public DBCollection GetTable(DB db, String tableName) {
        DBCollection table = db.getCollection(tableName);
        return table;
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new MongoDBDataSource();
        }
        return dataSource;
    }
}
