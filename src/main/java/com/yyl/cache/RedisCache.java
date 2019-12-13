package com.yyl.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: mybatis自定义二级缓存
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/2 14:56
 * @Version: 1.0
 */
public class RedisCache implements Cache {
    RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private String id;

    /**
     * 创建一个缓存实例
     * @param id 这个id就是当前mapper的namespace,也就是dao的类名，必须要有这个构造函数，因为mybatis在
     *           反射创建这个缓存实例的时候会使用这个构造函数进行反射
     */
    public RedisCache(final String id){
        if(id==null){
            logger.error("cache id is null" );
        }
        this.id = id;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisUtils.addObject(serializer.serialize(key),serializer.serialize(value));
    }

    @Override
    public Object getObject(Object key) {
        return serializer.deserialize(RedisUtils.getKey(serializer.serialize(key)));
    }

    @Override
    public Object removeObject(Object key) {
        return RedisUtils.delKey(serializer.serialize(key));
    }

    @Override
    public void clear() {
        Jedis jedis = null;
        try{
            jedis = RedisUtils.getJedis();
            jedis.flushDB();
            jedis.flushAll();
        }finally {
            jedis.close();
        }
    }

    @Override
    public int getSize() {
        return Integer.parseInt(RedisUtils.getJedis().dbSize().toString());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
