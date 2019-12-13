package com.yyl.typeHandler;

import com.yyl.enums.SexEnum;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description: 性别枚举TypeHandler
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 12:18
 * @Version: 1.0
 */
public class SexEnumTypeHandler extends EnumTypeHandler<SexEnum> {
    /**
     * 可以这样创建构造器，但是这样的话就不能使用局部的TypeHandler了
     * 因为必须制定JavaType mybatis反射的时候会将javaClass传给此对象创建构造器
     * 如果不存在就使用无参的构造，所以也可以使用下面的无参构造，无需指定JavaType
     * 这样就可以使用局部的TypeHandler了
     * @param type
     */
    public SexEnumTypeHandler(Class<SexEnum> type) {
        super(type);
    }
    public SexEnumTypeHandler() {
        super(SexEnum.class);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SexEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.getCode());
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return SexEnum.getSexEnum(rs.getString(columnName));
    }

    @Override
    public SexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return SexEnum.getSexEnum(rs.getString(columnIndex));
    }

    @Override
    public SexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return super.getNullableResult(cs, columnIndex);
    }
}
