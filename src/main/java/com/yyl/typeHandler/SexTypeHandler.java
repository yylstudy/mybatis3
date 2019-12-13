package com.yyl.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description: 处理性别的TypeHandler
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 11:24
 * @Version: 1.0
 */
public class SexTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if("1".equals(rs.getString(columnName))){
            return "男";
        }else{
            return "女";
        }

    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if("1".equals(rs.getString(columnIndex))){
            return "男";
        }else{
            return "女";
        }
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
