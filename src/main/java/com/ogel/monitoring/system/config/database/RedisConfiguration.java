package com.ogel.monitoring.system.config.database;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisConfiguration {

    private static final int REDIS_PORT = 6379;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());

        return template;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnProperty(prefix = "spring.redis", name = "embedded", havingValue = "true", matchIfMissing = false)
    RedisServer redisServer() throws IOException {
        RedisServer redisServer = new RedisServer(REDIS_PORT);

        return redisServer;
    }
}