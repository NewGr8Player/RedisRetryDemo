package com.xavier.retryable.redisretrydemo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class FinishedEventEntity extends ApplicationEvent {

    private String eventId;

    private String bizId;

    public FinishedEventEntity(String eventId, String bizId) {
        super(eventId);
        this.eventId = eventId;
        this.bizId = bizId;
    }

    public FinishedEventEntity(String eventId, String bizId, Clock clock) {
        super(eventId, clock);
        this.eventId = eventId;
        this.bizId = bizId;
    }
}
