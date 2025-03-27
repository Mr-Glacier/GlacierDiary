package com.glacier.glacierdiary.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote redis服务类
 * @since 2025/3/27 22:40
 */
@Service
public class RedisService {

    @Value("${redis.cache.default-expire}")
    private long defaultExpire;
    private final RedisTemplate<String, Object> cacheRedisTemplate;
    private final RedisTemplate<String, Object> messageRedisTemplate;

    public RedisService(@Qualifier("cacheRedisTemplate") RedisTemplate<String, Object> cacheRedisTemplate,
                        @Qualifier("messageRedisTemplate") RedisTemplate<String, Object> messageRedisTemplate) {
        this.cacheRedisTemplate = cacheRedisTemplate;
        this.messageRedisTemplate = messageRedisTemplate;
    }


    /**
     * 设置缓存信息
     *
     * @return 是否设置成功
     */
    public boolean setCache(String key, Object value) {
        try {
            cacheRedisTemplate.opsForValue().set(key, value, defaultExpire, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 设置缓存信息并指定过期时间（推荐方式）
     */
    public boolean setCacheWithExpire(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            cacheRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 获取缓存信息
     *
     * @return 缓存信息 ,可能返回 null
     */
    public Object getCache(String key) {
        return cacheRedisTemplate.opsForValue().get(key);
    }

    /**
     * 发送消息
     */
    public void sendMessage(String channel, Object message) {
        messageRedisTemplate.convertAndSend(channel, message);
    }


}
