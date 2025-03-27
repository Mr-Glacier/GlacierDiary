package com.glacier.glacierdiary.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 消息 Redis
 * @since 2025/3/27 22:39
 */
@Configuration
public class MessageRedisConfig {


    @Value("${spring.redis.message.host}")
    private String host;

    @Value("${spring.redis.message.port}")
    private int port;

    @Value("${spring.redis.message.password}")
    private String password;

    @Value("${spring.redis.message.database}")
    private int database;

    @Bean(name = "messageRedisConnectionFactory")
    public RedisConnectionFactory messageRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setPassword(password);
        config.setDatabase(database);
        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "messageRedisTemplate")
    public RedisTemplate<String, Object> messageRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(messageRedisConnectionFactory());
        return template;
    }

}
