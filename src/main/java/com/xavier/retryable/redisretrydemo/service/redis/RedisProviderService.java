package com.xavier.retryable.redisretrydemo.service;

import com.xavier.retryable.redisretrydemo.config.RedisConfig;
import com.xavier.retryable.redisretrydemo.entity.EmailInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class RedisProviderService {

    private RedisTemplate<String, EmailInfoEntity> redisTemplate;

    /**
     * 将消息传入Redis消息队列
     *
     * @param emailInfoEntity 邮件信息
     */
    public void publishMessage(EmailInfoEntity emailInfoEntity) {
        redisTemplate.opsForStream().add(ObjectRecord.create(RedisConfig.REDIS_MAIL_STREAM_KEY_NAME, emailInfoEntity));
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, EmailInfoEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
