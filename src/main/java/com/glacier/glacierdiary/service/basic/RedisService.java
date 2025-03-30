package com.glacier.glacierdiary.service.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote redis 服务类
 * @since 2025/1/20 1:57
 */
@Service
public class RedisService {

    private final JedisPool jedisPool;

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * <p>
     * 设置键值对到Redis
     * </p>
     */
    public void set(String key, String value, long milliseconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.psetex(key, milliseconds, value);
        }
    }

    /**
     * <p>
     * 根据key获取value
     * </p>
     */
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * <p>
     * 删除key
     * </p>
     *
     * @param key
     */
    public void delete(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }
}
