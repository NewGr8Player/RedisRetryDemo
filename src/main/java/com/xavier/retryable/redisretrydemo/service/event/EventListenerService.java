package com.xavier.retryable.redisretrydemo.service.event;

import com.xavier.retryable.redisretrydemo.entity.FinishedEventEntity;
import com.xavier.retryable.redisretrydemo.entity.MailEventEntity;
import com.xavier.retryable.redisretrydemo.service.redis.RedisProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventListenerService {

    private RedisProviderService redisProviderService;

    private RedisTemplate<String, Long> redisTemplate;

    @EventListener(classes = MailEventEntity.class, id = "handleMailEvent")
    public void mailEventHandler(MailEventEntity event) {
        log.info("响应MailEvent -> {}", event.getEventId());
        redisProviderService.publishMessage(event.getMailInfoEntity());
    }

    @EventListener(classes = FinishedEventEntity.class, id = "finishedEventHandler")
    public void finishedEventHandler(FinishedEventEntity event) {
        log.info("响应FinishedEvent -> {}", event.getEventId());
        redisTemplate.opsForValue().getAndDelete(event.getBizId());
    }

    @Autowired
    public void setRedisProviderService(RedisProviderService redisProviderService) {
        this.redisProviderService = redisProviderService;
    }

    @Autowired
    @Qualifier("longRedisTemplate")
    public void setRedisTemplate(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
