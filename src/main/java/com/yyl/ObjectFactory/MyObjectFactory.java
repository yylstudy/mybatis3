package com.yyl.ObjectFactory;

import com.yyl.page.PageHelper;
import com.yyl.page.PageResult;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.util.List;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/3 11:14
 * @Version: 1.0
 */
public class MyObjectFactory extends DefaultObjectFactory {
    private Properties properties;
    /**
     * <objectFactory>标签下<property>标签对象
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public <T> T create(Class<T> type) {
        T t = super.create(type);
        //如果创建的是PageResult对象，那么从ThreadLocal中获取分页查询总数
        if(t instanceof PageResult){
            ((PageResult) t).setTotal(PageHelper.getPageCount());
        }
        return t;
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        return super.create(type,constructorArgTypes,constructorArgs);
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return super.isCollection(type);
    }
}
