package com.xanadukeeper.glacierdiary.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * <p>
 *     Redis 多实例配置
 *     spring:
 *          redis:
 *              first:
 *                  host:127.0.0.1
 *                  port:6379
 *                  password:*******
 *                  database:0
 *                  jedis:
 *                      pool:
 *                          max-active: 8
 *                          max-idle: 8
 *                          min-idle: 0
 *                          max-wait: 1000
 *              second:
 *                  host:127.0.0.1
 *                  port:6380
 *                  password:*******
 *                  database:0
 *                  jedis:
 *                      pool:
 *                          max-active: 8
 *                          max-idle: 8
 *                          min-idle: 0
 *                          max-wait: 1000
 * </p>
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 多例 Redis使用配置
 * @since 2025/1/24 9:58
 */
@Configuration
public class RedisMultipleConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisMultipleConfig.class);
    private static final String REDIS_PREFIX = "spring.redis";
    private static final String REDIS_FIRST_PREFIX = REDIS_PREFIX + ".first";
    private static final String REDIS_SECOND_PREFIX = REDIS_PREFIX + ".second";

    private final Environment environment;

    public RedisMultipleConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 创建 jedis 连接池配置 -- 实例A
     */
    @Bean(name = "jedisPoolConfigFirst")
    public JedisPoolConfig jedisPoolConfigFirst() {
        return createJedisPoolConfig(REDIS_FIRST_PREFIX);
    }

    /**
     * 创建 jedis 连接池配置 -- 实例B
     */
    @Bean(name = "jedisPoolConfigSecond")
    public JedisPoolConfig jedisPoolConfigSecond() {
        return createJedisPoolConfig(REDIS_SECOND_PREFIX);
    }

    /**
     * 读取环境变量, 创建 JedisPoolConfig
     */
    public JedisPoolConfig createJedisPoolConfig(String redisPrefix) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(environment.getProperty(REDIS_PREFIX + redisPrefix + ".jedis.pool.max-active", Integer.class, 8));
        jedisPoolConfig.setMaxIdle(environment.getProperty(REDIS_PREFIX + redisPrefix + ".jedis.pool.max-idle", Integer.class, 8));
        jedisPoolConfig.setMinIdle(environment.getProperty(REDIS_PREFIX + redisPrefix + ".jedis.pool.min-idle", Integer.class, 1));
        jedisPoolConfig.setMaxWait(Duration.ofMillis(environment.getProperty(REDIS_PREFIX + redisPrefix + ".jedis.pool.max-wait", Long.class, 1000L)));
        logger.info("Initialized JedisPoolConfig with maxActive={}, maxIdle={}, minIdle={}, maxWait={}", jedisPoolConfig.getMaxTotal(), jedisPoolConfig.getMaxIdle(), jedisPoolConfig.getMinIdle(), jedisPoolConfig.getMaxWaitDuration());
        return jedisPoolConfig;
    }

    @Bean(name = "jedisPoolFirst")
    public JedisPool jedisPoolFirst(@Qualifier("jedisPoolConfigFirst") JedisPoolConfig jedisPoolConfig) {
        return createJedisPool(jedisPoolConfig, REDIS_PREFIX + REDIS_FIRST_PREFIX);
    }

    @Bean(name = "jedisPoolSecond")
    public JedisPool jedisPoolSecond(@Qualifier("jedisPoolConfigSecond") JedisPoolConfig jedisPoolConfig) {
        return createJedisPool(jedisPoolConfig, REDIS_PREFIX + REDIS_SECOND_PREFIX);
    }

    private JedisPool createJedisPool(JedisPoolConfig jedisPoolConfig, String prefix) {
        try {
            String host = getPropertyValue(prefix + ".host");
            int port = Integer.parseInt(getPropertyValue(prefix + ".port", "6379"));
            String password = getPropertyValue(prefix + ".password", "");
            int database = Integer.parseInt(getPropertyValue(prefix + ".database", "0"));
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 2000, password, database);
            logger.info("Connected to Redis at {}:{}", host, port);
            return jedisPool;
        } catch (Exception e) {
            logger.error("Failed to initialize JedisPool", e);
            throw new RuntimeException("Failed to initialize JedisPool", e);
        }
    }

    /**
     * 获取环境变量的值
     */
    private String getPropertyValue(String key) {
        return environment.getProperty(key);
    }

    /**
     * 获取环境变量的值，如果没有指定默认值
     */
    private String getPropertyValue(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }


}
