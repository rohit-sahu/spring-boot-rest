package io.devzona.springboot.emailconsumer.service.impl;

import io.devzona.springboot.emailconsumer.model.Employee;
import io.devzona.springboot.emailconsumer.service.KafkaConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    @Autowired
    Environment environment;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    @KafkaListener(topics = {"#{'${spring.kafka.consumer.topic}'.split(',')}"})
    public void receive(@Headers MessageHeaders headers, @Payload List<Employee> data) {
        log.info("Consuming the topic {} with message header {} and data is {}", environment.getProperty("spring.kafka.consumer.topic"), headers.toString(), data.toString());
        latch.countDown();
    }
}
