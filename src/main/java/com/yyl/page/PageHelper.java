package com.yyl.page;

import com.yyl.enums.DatabaseIdProviderEnum;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/12 19:00
 * @Version: 1.0
 */
public class PageHelper {
    private static ThreadLocal<Long> pageCount =  ThreadLocal.withInitial(()->0L);
    /**
     * 获取总数
     * @param connection
     * @param sql
     * @param proxyTarget
     * @return
     * @throws Exception
     */
    public static long getTotal(Connection connection, String sql, RoutingStatementHandler proxyTarget) throws Exception{
        CountSqlParser countSqlParser = new CountSqlParser();
        sql = countSqlParser.getSmartCountSql(sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(sql);
            proxyTarget.parameterize(ps);
            rs = ps.executeQuery();
            long total = 0;
            if(rs.next()){
                total = rs.getLong(1);
            }
            return total;
        }catch (Exception e){
            throw new RuntimeException("分页查询获取总数失败");
        }finally {
            if(ps!=null){
                ps.close();
            }if(rs!=null){
                rs.close();
            }
        }
    }

    public static String getPageSql(Connection connection,String sql,PageInfo pageInfo) throws Exception{
        String dbName = connection.getMetaData().getDatabaseProductName();
        DatabaseIdProviderEnum database = DatabaseIdProviderEnum.getDatabaseIdEnum(dbName);
        if(database==DatabaseIdProviderEnum.MYSQL){
            return sql+" limit "+pageInfo.getPageNo()*pageInfo.getPageSize()+" ,"+pageInfo.getPageSize();
        }
        return sql;
    }

    public static void setPageCount(long total){
        pageCount.set(total);
    }
    public static long  getPageCount(){
        return pageCount.get();
    }
}
