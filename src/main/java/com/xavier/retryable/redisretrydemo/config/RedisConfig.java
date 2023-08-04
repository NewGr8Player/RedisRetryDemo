package com.xavier.retryable.redisretrydemo.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    public static final String REDIS_MAIL_STREAM_KEY_NAME = "RedisMailQueue";

    @Bean("longRedisTemplate")
    public RedisTemplate<String, Long> longRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        return template;
    }
}

