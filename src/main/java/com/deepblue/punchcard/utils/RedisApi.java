package com.deepblue.punchcard.utils;


import com.deepblue.punchcard.constant.ProjectConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis工具类
 */
@Component
public class RedisApi {
    private Logger logger = LoggerFactory.getLogger(RedisApi.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置key和value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
        return true;
    }

    /**
     * 设置key value 并且设置有效期
     *
     * @param key
     * @param seconds 有效期
     * @param value
     * @return
     */
    public boolean set(String key, long seconds, String value) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
        expire(key, seconds);
        return true;
    }

    /**
     * 判断某个key是否存在
     *
     * @param key
     * @return
     */
    public boolean exist(String key) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        Object value = vo.get(key);
        return EmptyUtils.isEmpty(value) ? false : true;
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    /**
     * 根据key删除value
     *
     * @param key
     */
    public void delete(String key) {
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            //设置序列化Value的实例化对象
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置key并且加所
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public Boolean setnx(final String key, final String value) throws Exception {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) {
                boolean flag = false;
                try {
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    //设置序列化Value的实例化对象
                    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    byte keys[] = stringRedisSerializer.serialize(key);
                    byte values[] = stringRedisSerializer.serialize(value);
                    flag = redisConnection.setNX(keys, values);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    return flag;
                }
            }
        });
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param expireTime
     * @return
     */
    public Boolean expire(final String key, final Long expireTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                boolean flag = false;
                try {
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    //设置序列化Value的实例化对象
                    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    byte keys[] = stringRedisSerializer.serialize(key);
                    flag = redisConnection.expire(keys, expireTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return flag;
            }
        });
    }

    /**
     * 给key加锁
     *
     * @param key
     * @return
     */
    public boolean lock(String key) {
        boolean flag = false;
        try {
            String lockKey = generateLockKey(key);
            flag = setnx(lockKey, "lock");
            if (flag) {
                System.out.println(expire(lockKey, ProjectConstants.Redis_Expire.DEFAULT_EXPIRE));
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public Object getValueNx(String key) {
        String lockKey = generateLockKey(key);
        Object object = get(lockKey);
        return object;
    }

    /**
     * 解锁
     *
     * @param key
     */
    public void unlock(String key) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        String lockKey = generateLockKey(key);
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.del(lockKey.getBytes());
        connection.close();
    }

    private String generateLockKey(String key) {
        return String.format("LOCK:%s", key);
    }

    public boolean validate(String token) {
        return exist(token);
    }


}
