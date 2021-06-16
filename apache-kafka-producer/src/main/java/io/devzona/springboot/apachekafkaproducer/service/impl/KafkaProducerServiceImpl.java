package io.devzona.springboot.apachekafkaproducer.service.impl;

import io.devzona.springboot.apachekafkaproducer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String topic, Object data) {
        kafkaTemplate.send(topic, data);
    }
}
