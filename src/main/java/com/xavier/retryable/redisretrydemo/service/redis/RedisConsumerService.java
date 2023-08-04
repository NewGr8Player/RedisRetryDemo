package com.xavier.retryable.redisretrydemo.service;

import com.xavier.retryable.redisretrydemo.config.RedisConfig;
import com.xavier.retryable.redisretrydemo.entity.EmailInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


@Slf4j
@Service
@Transactional
public class RedisConsumerService {

    private RedisTemplate<String,EmailInfoEntity> redisTemplate;
    
    private RedisConnectionFactory connectionFactory;

    @PostConstruct
    public void init() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, EmailInfoEntity>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .targetType(EmailInfoEntity.class)
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, EmailInfoEntity>> listenerContainer = StreamMessageListenerContainer.create(connectionFactory, options);
        listenerContainer.receive(
                StreamOffset.create(RedisConfig.REDIS_MAIL_STREAM_KEY_NAME, ReadOffset.lastConsumed()),
                this::messageHandler
        );

        listenerContainer.start();
    }

    public void messageHandler(ObjectRecord<String, ?> message) {
        log.info("Receive -> {}", message);
        redisTemplate.opsForStream().delete(message); /* 消费后从Redis中删除 */
    }

    @Autowired
    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, EmailInfoEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
