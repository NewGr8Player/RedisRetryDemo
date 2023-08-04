package com.xavier.retryable.redisretrydemo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class MailEventEntity extends ApplicationEvent {

    private String eventId;

    private MailInfoEntity mailInfoEntity;

    public MailEventEntity(String eventId, MailInfoEntity mailInfoEntity) {
        super(eventId);
        this.eventId = eventId;
        this.mailInfoEntity = mailInfoEntity;
    }

    public MailEventEntity(String eventId, MailInfoEntity mailInfoEntity, Clock clock) {
        super(eventId, clock);
        this.eventId = eventId;
        this.mailInfoEntity = mailInfoEntity;
    }
}
