package com.xavier.retryable.redisretrydemo.service.redis;

import com.xavier.retryable.redisretrydemo.config.RedisConfig;
import com.xavier.retryable.redisretrydemo.config.RetryProperties;
import com.xavier.retryable.redisretrydemo.entity.MailInfoEntity;
import com.xavier.retryable.redisretrydemo.service.event.EventPublisherService;
import com.xavier.retryable.redisretrydemo.service.mail.MailSenderHistoryService;
import com.xavier.retryable.redisretrydemo.service.mail.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


@Slf4j
@Service
@Transactional
public class RedisConsumerService {

    private StringRedisTemplate stringRedisTemplate;

    private RedisTemplate<String, Long> longRedisTemplate;

    private RedisConnectionFactory connectionFactory;

    private MailSenderService mailSenderService;

    private MailSenderHistoryService mailSenderHistoryService;

    private EventPublisherService eventPublisherService;

    private RetryProperties retryProperties;

    @PostConstruct
    public void init() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, MailInfoEntity>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .targetType(MailInfoEntity.class)
                        .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, MailInfoEntity>> listenerContainer = StreamMessageListenerContainer.create(
                connectionFactory,
                options
        );

        listenerContainer.receive(
                StreamOffset.create(RedisConfig.REDIS_MAIL_STREAM_KEY_NAME, ReadOffset.lastConsumed()),
                this::messageHandler
        );

        listenerContainer.start();
    }

    public void messageHandler(ObjectRecord<String, MailInfoEntity> message) {
        log.info("Receive -> {}", message);

        MailInfoEntity mailInfoEntity = message.getValue();
        if (!mailSenderService.send(mailInfoEntity)) {/* 发送失败 */
            long currentCnt = Optional.ofNullable(longRedisTemplate.opsForValue().increment(mailInfoEntity.getId())).orElse(1L);
            if (currentCnt <= retryProperties.getTimes()) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        log.info("进行第{}次重试...{} - {}", currentCnt, mailInfoEntity.getTo(), mailInfoEntity.getSubject());
                        eventPublisherService.publishEvent(mailInfoEntity);
                    }
                }, retryProperties.getDuration());
            } else {
                log.info("最终失败逻辑");
                mailInfoEntity.setRetryComment("最终失败逻辑");
                mailSenderHistoryService.save(mailInfoEntity);
                eventPublisherService.publishEvent(mailInfoEntity.getId());
            }
        }
        stringRedisTemplate.opsForStream().delete(message); /* 成功消费后从Redis中删除 */

    }

    @Autowired
    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @Autowired
    public void setMailSenderHistoryService(MailSenderHistoryService mailSenderHistoryService) {
        this.mailSenderHistoryService = mailSenderHistoryService;
    }

    @Autowired
    public void setEventPublisherService(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setRetryProperties(RetryProperties retryProperties) {
        this.retryProperties = retryProperties;
    }

    @Autowired
    @Qualifier("longRedisTemplate")
    public void setLongRedisTemplate(RedisTemplate<String, Long> longRedisTemplate) {
        this.longRedisTemplate = longRedisTemplate;
    }
}
