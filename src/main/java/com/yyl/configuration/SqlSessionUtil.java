package com.yyl.configuration;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/29 11:29
 * @Version: 1.0
 */
public class SqlSessionUtil {
    private static volatile SqlSessionFactory sqlSessionFactory = null;
    private static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory==null){
            synchronized (SqlSessionUtil.class){
                if(sqlSessionFactory==null){
                    try{
                        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession getSqlSession(){
        return getSqlSessionFactory().openSession();
    }
}
