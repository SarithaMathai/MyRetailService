package com.retail.common.dataservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.retail.common.QueryByIdSpec;
import com.retail.common.TableColumnUtils;
import com.retail.common.TableColumnUtils.DataType;

/**
 * Data Source representing the HSQLDB
 *
 */
public class HSQLDBDataSource implements DataSource {

    final static Logger logger = Logger.getLogger(DataSourceFactory.class.getName());
    private Connection connection;
    String driver = "org.hsqldb.jdbc.JDBCDriver";

    public HSQLDBDataSource() {
        // Performs required initialization and acquire a jdbc connection.
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSource getDBSourceInstance() {
        return new HSQLDBDataSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject executeQuery(QueryByIdSpec spec) {
        JSONObject json = new JSONObject();
        try {

            PreparedStatement st = connection.prepareStatement(buildSelectStatementForId(spec));
            st.setInt(1, spec.getIdValue());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                for (String column : spec.getSelects()) {
                    String dbField = TableColumnUtils.getBeanToDbMap().get(column);
                    DataType dataType = TableColumnUtils.getDataType(dbField);
                    try {
                        json.put(spec.getIdAttributeName(), spec.getIdValue());
                        if (DataType.STRING.equals(dataType)) {
                            json.put(column, rs.getString(dbField));

                        } else if (DataType.DOUBLE.equals(dataType)) {
                            json.put(column, rs.getDouble(dbField));

                        } else if (DataType.INT.equals(dataType)) {
                            json.put(column, rs.getInt(dbField));

                        }
                    } catch (JSONException e) {                        
                        e.printStackTrace();
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("SqlException encountered", e);
        }
        return json;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuery(QueryByIdSpec spec) {
        try {
            PreparedStatement st = connection.prepareStatement(buildUpdateStatementForId(spec));
            SortedMap<String, String> updateValueMap = spec.getUpdateValueMap();

            int count = 1;
            for (String column : updateValueMap.keySet()) {
                String dbField = TableColumnUtils.getBeanToDbMap().get(column);
                DataType dataType = TableColumnUtils.getDataType(dbField);
                if (DataType.STRING.equals(dataType)) {
                    st.setString(count++, updateValueMap.get(column));

                } else if (DataType.DOUBLE.equals(dataType)) {
                    st.setDouble(count++, Double.parseDouble(updateValueMap.get(column)));

                } else if (DataType.INT.equals(dataType)) {
                    st.setInt(count++, Integer.parseInt(updateValueMap.get(column)));
                }
            }
            st.setInt(2, spec.getIdValue());
            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            logger.error("SqlException encountered", e);
        }
    }

    /**
     * Helper method to build a HSQLDB specific SQL select statements based on QueryByIdSpec.
     * 
     * @param spec
     * @return query string.
     */
    private String buildSelectStatementForId(QueryByIdSpec spec) {
        StringBuilder statement = new StringBuilder();
        if (spec.hasAllQueryElementsForSelect()) {
            statement.append("select ");
            boolean firstSelect = true;
            for (String column : spec.getSelects()) {
                if (!firstSelect) {
                    statement.append(",");
                }
                firstSelect = false;

                statement.append(TableColumnUtils.getBeanToDbMap().get(column));
            }
            statement.append(" from ").append(spec.getTableName());
            statement.append(" where ").append(TableColumnUtils.getBeanToDbMap().get(spec.getIdAttributeName()))
                    .append(" = ?");
        }

        return statement.toString();
    }

    /**
     * Helper method to build a HSQLDB specific SQL update statements based on QueryByIdSpec.
     * 
     * @param spec
     * @return query string.
     */
    private String buildUpdateStatementForId(QueryByIdSpec spec) {

        StringBuilder statement = new StringBuilder();
        if (spec.hasAllQueryElementsForUpdate()) {
            statement.append("update  ").append(spec.getTableName());
            boolean firstSelect = true;
            for (String column : spec.getUpdateValueMap().keySet()) {
                if (firstSelect) {
                    statement.append(" set ");
                } else {
                    statement.append(" and ");
                }
                firstSelect = false;
                statement.append(TableColumnUtils.getBeanToDbMap().get(column)).append(" = ?");
            }
            statement.append(" where ").append(TableColumnUtils.getBeanToDbMap().get(spec.getIdAttributeName()))
                    .append(" = ?");
        }

        return statement.toString();
    }

    /**
     * database initialization on creating an instance.
     */
    private void initialize() {
        acquireConnection();
    }

    private void acquireConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(DBConfiguration.HSQLDB_JDBC_URL, DBConfiguration.HSQLDB_JDBC_USER,
                    DBConfiguration.HSQLDB_JDBC_PASSWORD);
        } catch (Exception e) {
            logger.error("Connection Failed! Check output console", e);
        }
    }
}
