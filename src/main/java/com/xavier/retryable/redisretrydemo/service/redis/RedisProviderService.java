package com.xavier.retryable.redisretrydemo.service.redis;

import com.xavier.retryable.redisretrydemo.config.RedisConfig;
import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
public class RedisProviderService {

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将消息传入Redis消息队列
     *
     * @param mailInfoEntity 邮件信息
     */
    public void publishMessage(MailInfoEntity mailInfoEntity) {
        stringRedisTemplate.opsForStream().add(ObjectRecord.create(RedisConfig.REDIS_MAIL_STREAM_KEY_NAME, Map.of(
                mailInfoEntity.getId(),
                mailInfoEntity.toJson()
        )));
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
