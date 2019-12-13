package com.yyl.cache;//package com.yyl.cache;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 6379;
    private static final String AUTH="redis";
    private static int   MAX_ACTIVE = 1024;
    private static int   MAX_IDLE = 200;
    private static int   MAX_WAIT = 10000;
    private static int   TIMEOUT = 10000;
    private static boolean BORROW = true;
    private static JedisPool pool = null;
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    /**
     * 初始化线程池
     */
    static
    {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(BORROW);
        pool = new JedisPool(config, IP, PORT, TIMEOUT);
    }


    /**
     * 获取连接
     */
    public static synchronized Jedis getJedis()
    {
        try
        {
            if(pool != null)
            {
                return pool.getResource();
            }
            else
            {
                return null;
            }
        }
        catch (Exception e) {
            logger.error("连接池连接异常");
            return null;
        }

    }
    /**
     * @Description:设置失效时间
     * @param @param key
     * @param @param seconds
     * @param @return
     * @return boolean 返回类型
     */
    public static void disableTime(String key,int seconds)
    {
        Jedis jedis = null;
        try
        {
            jedis = getJedis();
            jedis.expire(key, seconds);

        }
        catch (Exception e) {
            logger.debug("设置失效失败.");
        }finally {
            getColse(jedis);
        }
    }


    /**
     * @Description:插入对象
     * @param @param key
     * @param @param obj
     * @param @return
     * @return boolean 返回类型
     */
    public static boolean addObject(byte[] key,byte[] obj)
    {

        Jedis jedis = null;
        try{
            jedis = getJedis();
            String code = jedis.set(key, obj);
            if("ok".equals(code))
            {
                return true;
            }
        }
        catch (Exception e) {
            logger.error("插入数据有异常.",e);
            return false;
        }finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * @Description:获取key
     * @param @param key
     * @param @return
     * @return boolean 返回类型
     */
    public static byte[] getKey(byte[] key)
    {
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.get(key);
        }
        catch (Exception e) {
            logger.debug("删除key异常.");
            return null;
        }finally {
            getColse(jedis);
        }
    }
    /**
     * @Description:删除key
     * @param @param key
     * @param @return
     * @return boolean 返回类型
     */
    public static boolean delKey(byte[] key)
    {
        Jedis jedis = null;
        try
        {
            jedis = getJedis();
            Long code = jedis.del(key);
            if(code > 1)
            {
                return true;
            }
        }
        catch (Exception e) {
            logger.debug("删除key异常.");
            return false;
        }finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * @Description: 关闭连接
     * @param @param jedis
     * @return void 返回类型
     */

    public static void getColse(Jedis jedis)
    {
        if(jedis != null)
        {
            jedis.close();
        }
    }
}
