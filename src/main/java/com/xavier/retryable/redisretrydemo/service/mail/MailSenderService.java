package com.xavier.retryable.redisretrydemo.service.mail;

import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import com.xavier.retryable.redisretrydemo.service.event.EventPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
public class MailSenderService {

    private MailSenderHistoryService mailSenderHistoryService;

    private EventPublisherService eventPublisherService;

    public void sendWithHistory(MailInfoEntity mailInfoEntity) {
        boolean result = send(mailInfoEntity);
        if (!result) {
            retry(mailInfoEntity);
        }
        mailSenderHistoryService.save(mailInfoEntity);
    }

    public boolean send(MailInfoEntity mailInfoEntity) {
        boolean result;
        try {
            if (Objects.equals("ffff", mailInfoEntity.getId())) {
                throw new RuntimeException("GGGGG");
            }
            result = true;
            eventPublisherService.publishEvent(mailInfoEntity.getId());
        } catch (Exception e) {
            log.error("邮件发送失败: {} - {} -> {}", mailInfoEntity.getTo(), mailInfoEntity.getSubject(), e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 重试
     *
     * @param mailInfoEntity 邮件信息
     */
    public void retry(MailInfoEntity mailInfoEntity) {
        eventPublisherService.publishEvent(mailInfoEntity);
    }

    @Autowired
    public void setMailSenderHistoryService(MailSenderHistoryService mailSenderHistoryService) {
        this.mailSenderHistoryService = mailSenderHistoryService;
    }

    @Autowired
    public void setEventPublisherService(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }
}
