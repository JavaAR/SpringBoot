package com.deepblue.punchcard.utils.tempUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型转化java类型
 */
public class TypeConstant {

    private static Map<String, String> typeMap = new HashMap<String, String>();

    static {
        typeMap.put("BIGINT", "Long");
        typeMap.put("INT", "Integer");
        typeMap.put("VARCHAR", "String");
        typeMap.put("TEXT", "String");
        typeMap.put("DATETIME", "Date");
        typeMap.put("DECIMAL", "Double");
    }

    public static void addType(String columnType, String javaType) {
        typeMap.put(columnType, javaType);
    }

    public static String getJavaType(String columnType) {
        return typeMap.get(columnType);
    }
}
