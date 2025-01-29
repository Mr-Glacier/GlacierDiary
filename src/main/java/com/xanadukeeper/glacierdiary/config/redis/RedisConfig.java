package com.xanadukeeper.glacierdiary.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote Jedis配置类
 * @since 2024/10/1 15:48
 */

/**
 * Jedis配置类(单个实例配置)
 * 配置文件形式:
 *spring:
 *   redis:
 *     host: 127.0.0.1  # Redis服务器的主机地址
 *     port: 6379            # Redis服务器的端口
 *     password: *********** # Redis服务器的密码（如果需要）
 *     database: 4           # 使用的Redis数据库索引，默认是0
 *     # 配置 redis 连接池配置
 *     jedis:
 *       pool:
 *         max-active: 8     # 最大活跃连接数，默认值为8
 *         max-idle: 8       # 最大空闲连接数，默认值为8
 *         min-idle: 0       # 最小空闲连接数，默认值为0
 *         max-wait: 1000    # 当没有可用连接时，最大等待时间（毫秒），默认值为-1，即无限期等待
 *
 * @author Mr-Glacier
 * @since 2025/01/21 15:05
 */
@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    private static final String REDIS_PREFIX = "spring.redis";

    private final String host;
    private final int port;
    private final String password;
    private final int database;
    private final int maxActive;
    private final int maxIdle;
    private final int minIdle;
    private final long maxWait;

    public RedisConfig(
            @Value("${" + REDIS_PREFIX + ".host}") String host,
            @Value("${" + REDIS_PREFIX + ".port:6379}") int port, // 提供默认端口
            @Value("${" + REDIS_PREFIX + ".password:}") String password, // 空字符串作为默认值
            @Value("${" + REDIS_PREFIX + ".database:0}") int database, // 默认数据库索引
            @Value("${" + REDIS_PREFIX + ".jedis.pool.max-active:8}") int maxActive,
            @Value("${" + REDIS_PREFIX + ".jedis.pool.max-idle:8}") int maxIdle,
            @Value("${" + REDIS_PREFIX + ".jedis.pool.min-idle:0}") int minIdle,
            @Value("${" + REDIS_PREFIX + ".jedis.pool.max-wait:1000}") long maxWait) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.database = database;
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.maxWait = maxWait;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);

        // 当连接池耗尽时，是否阻塞等待
        poolConfig.setBlockWhenExhausted(true);
        // 设置最大等待时间
        poolConfig.setMaxWait(Duration.ofMillis(maxWait));
        logger.info("Initialized JedisPoolConfig with maxActive={}, maxIdle={}, minIdle={}, maxWait={}",
                maxActive, maxIdle, minIdle, maxWait);
        return poolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        try {
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 2000, password, database);
            logger.info("Connected to Redis at {}:{}", host, port);
            return jedisPool;
        } catch (Exception e) {
            logger.error("Failed to initialize JedisPool", e);
            throw new RuntimeException("Failed to initialize JedisPool", e);
        }
    }
}