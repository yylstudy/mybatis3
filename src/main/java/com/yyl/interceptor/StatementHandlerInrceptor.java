package com.yyl.interceptor;

import com.yyl.page.PageHelper;
import com.yyl.page.PageInfo;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: 基于StatementHandler的分页拦截器，如果正常配置，这种拦截器会有个缺点
 *               非select语句也会执行此拦截的逻辑，所以最好在创建StatementHandler的时候就根据参数
 *               判断是否代理此StatementHandler
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/12 17:27
 * @Version: 1.0
 */
@Intercepts(@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class}))
public class StatementHandlerInrceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //参数对象
        Object[] args = invocation.getArgs();
        Connection connection = (Connection)args[0];
        Method proxyMethod = invocation.getMethod();
        //被代理对象
        MetaObject routingStatementHandlerMetaObject = SystemMetaObject.forObject(invocation.getTarget());
        String sql = (String)routingStatementHandlerMetaObject.getValue("delegate.boundSql.sql");
        Object parameterObject = routingStatementHandlerMetaObject.getValue("delegate.boundSql.parameterObject");
        //获取PageInfo对象，这个是否创建代理对象的时候判断过了，所以这里不用判空
        PageInfo pageInfo = getPageInfo(parameterObject);
        //要执行的sql语句
        long total = PageHelper.getTotal(connection,sql,(RoutingStatementHandler)invocation.getTarget());
        PageHelper.setPageCount(total);
        String pageSql = PageHelper.getPageSql(connection,sql,pageInfo);
        //组装分页sql后，再把此sql赋值给BoundSql
        routingStatementHandlerMetaObject.setValue("delegate.boundSql.sql",pageSql);
        return proxyMethod.invoke(invocation.getTarget(),args);
    }

    /**
     * 从参数中获取PageInfo
     * @param parameterObject
     * @return
     */
    private PageInfo getPageInfo(Object parameterObject){
        if(parameterObject instanceof Map){
            return (PageInfo)((Map) parameterObject).values().stream().filter(param->param instanceof PageInfo)
                    .findFirst().orElse(null);
        }else{
            if(parameterObject instanceof PageInfo){
                return (PageInfo)parameterObject;
            }
        }
        return null;
    }

    /**
     * 判断是否作用于此拦截器
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        //只动态代理StatementHandler对象
        if(target instanceof StatementHandler){
            PreparedStatementHandler handler = (PreparedStatementHandler)SystemMetaObject
                    .forObject(target).getValue("delegate");
            Object obj = handler.getParameterHandler().getParameterObject();
            PageInfo pageInfo = getPageInfo(obj);
            //存在PageInfo参数才尝试去创建动态代理对象，这是因为基于StatementHandler的prepared方法
            //在insert、update、delete的时候也会用到，所以这里在创建代理对象的时候就判断，减少
            //创建不必要的代理对象
            if(pageInfo!=null){
                return Plugin.wrap(target, this);
            }
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
