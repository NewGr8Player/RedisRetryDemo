package com.xavier.retryable.redisretrydemo.api;

import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import com.xavier.retryable.redisretrydemo.service.mail.MailSenderService;
import com.xavier.retryable.redisretrydemo.service.redis.RedisProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailApi {

    private MailSenderService mailSenderService;

    @GetMapping("/test/{param}")
    public String testApi(@PathVariable String param) {
        return "Success: " + param;
    }

    @GetMapping("/testSend/{param}")
    public String testSendApi(@PathVariable String param) {
        MailInfoEntity mailInfoEntity = new MailInfoEntity();
        mailInfoEntity.setId(param);
        mailInfoEntity.setTo("fengzeming54@126.com");
        mailInfoEntity.setSubject("JustTest");
        mailInfoEntity.setContent("NothingButX");
        mailSenderService.sendWithHistory(mailInfoEntity);
        return "Success: " + param;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }
}
