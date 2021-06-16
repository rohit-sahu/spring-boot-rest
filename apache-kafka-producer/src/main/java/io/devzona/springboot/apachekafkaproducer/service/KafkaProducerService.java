package io.devzona.springboot.apachekafkaproducer.service;

public interface KafkaProducerService {

    void send(String topic, Object data);
}
