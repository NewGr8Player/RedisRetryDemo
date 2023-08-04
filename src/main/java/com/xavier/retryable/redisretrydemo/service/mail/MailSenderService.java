package com.xavier.retryable.redisretrydemo.service;

import com.xavier.retryable.redisretrydemo.entity.EmailInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MailSenderService {
    public void send(EmailInfoEntity emailInfoEntity){

    }


    public void retry(EmailInfoEntity emailInfoEntity){
        try{

        } catch (Exception e) {

        } finally {
            // TODO saveDB
        }
    }
}
