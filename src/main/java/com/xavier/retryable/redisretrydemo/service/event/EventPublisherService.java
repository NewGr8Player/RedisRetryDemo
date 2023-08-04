package com.xavier.retryable.redisretrydemo.service.event;

import com.xavier.retryable.redisretrydemo.entity.FinishedEventEntity;
import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import com.xavier.retryable.redisretrydemo.entity.MailEventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class EventPublisherService {


    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(MailInfoEntity mailInfo) {
        String eventId = UUID.randomUUID().toString();
        log.info("发布MailEvent -> {}", eventId);
        applicationEventPublisher.publishEvent(new MailEventEntity(eventId, mailInfo));
    }

    public void publishEvent(String bizId) {
        String eventId = UUID.randomUUID().toString();
        log.info("发布FinishedEvent -> {}", eventId);
        applicationEventPublisher.publishEvent(new FinishedEventEntity(eventId, bizId));
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
