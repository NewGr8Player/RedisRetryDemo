package com.xavier.retryable.redisretrydemo.service.mail;

import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MailSenderHistoryService {
    public void save(MailInfoEntity mailInfoEntity) {
        log.info("Save to database -> {}", mailInfoEntity);
    }
}
