package com.yyl.databaseIdProvider;

import com.yyl.enums.DatabaseIdProviderEnum;
import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description: 自定义数据库厂商
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 13:53
 * @Version: 1.0
 */
public class MyDatabaseIdProvider implements DatabaseIdProvider {
    @Override
    public void setProperties(Properties p) {

    }

    /**
     * 根据数据源获取数据库厂商名称
     * @param dataSource
     * @return
     * @throws SQLException
     */
    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            String dbName = connection.getMetaData().getDatabaseProductName();
            return DatabaseIdProviderEnum.getDatabaseId(dbName);
        }finally {
            if(connection!=null){
                connection.close();
            }
        }
    }
}
