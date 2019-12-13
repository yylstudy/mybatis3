package com.yyl.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 13:57
 * @Version: 1.0
 */
public enum DatabaseIdProviderEnum {
    MYSQL("MySQL","mysql"),
    ORACLE("Oracle","oracle"),
    DB2("DB2","db2");
    private static Map<String,String> map = new HashMap<>();
    private static Map<String,DatabaseIdProviderEnum> mapEnum = new HashMap<>();
    static {
        for(DatabaseIdProviderEnum databaseIdProviderEnum:DatabaseIdProviderEnum.values()){
            map.put(databaseIdProviderEnum.name,databaseIdProviderEnum.showName);
            mapEnum.put(databaseIdProviderEnum.name,databaseIdProviderEnum);
        }
    }

    public static String getDatabaseId(String name){
        return map.get(name);
    }
    public static DatabaseIdProviderEnum getDatabaseIdEnum(String name){
        return mapEnum.get(name);
    }

    private String name;
    private String showName;

    DatabaseIdProviderEnum(String name, String showName) {
        this.name = name;
        this.showName = showName;
    }

}
