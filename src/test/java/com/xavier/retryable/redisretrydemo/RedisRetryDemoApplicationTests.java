package com.xavier.retryable.redisretrydemo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@Profile("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RedisRetryDemoApplication.class)
public class RedisRetryDemoApplicationTests {

}
