package com.retail.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TableColumnUtils {

    public static final String CURRENCY_CODE = "currencyCode";

    public static final String VALUE = "value";

    public static final String PROD_CATEGORY = "prodCategory";

    public static final String PROD_NAME = "prodName";

    public static final String PROD_ID = "prodId";

    public enum DataType {
        INT, DOUBLE, STRING
    }

    static List<String> intfields = Arrays.asList("prod_id");

    static List<String> stringfields = Arrays.asList("prod_name", "prod_category", "currency");

    static List<String> doublefields = Arrays.asList("current_price");

    static HashMap<String, String> beanToDbMap = new HashMap<String, String>();

    public static HashMap<String, String> getBeanToDbMap() {
        if (beanToDbMap.isEmpty()) {
            loadFieldMap();
        }
        return beanToDbMap;
    }

    public static DataType getDataType(String fieldName) {
        if (stringfields.contains(fieldName)) {
            return DataType.STRING;
        } else if (intfields.contains(fieldName)) {
            return DataType.INT;
        } else if (doublefields.contains(fieldName)) {
            return DataType.DOUBLE;
        }
        return null;
    }

    private static void loadFieldMap() {
        beanToDbMap.put(PROD_ID, "prod_id");
        beanToDbMap.put(PROD_NAME, "prod_name");
        beanToDbMap.put(PROD_CATEGORY, "prod_category");
        beanToDbMap.put(VALUE, "current_price");
        beanToDbMap.put(CURRENCY_CODE, "currency");

    }
}