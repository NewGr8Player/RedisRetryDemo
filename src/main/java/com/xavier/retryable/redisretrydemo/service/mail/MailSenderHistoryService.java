package com.xavier.retryable.redisretrydemo.service;

import com.xavier.retryable.redisretrydemo.entity.EmailInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MailSenderHistoryService {
    public void save(EmailInfoEntity emailInfoEntity) {
        log.info("Save to database -> {}", emailInfoEntity);
    }
}
