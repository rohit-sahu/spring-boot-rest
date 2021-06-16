package io.devzona.springboot.emailproducer.service;

public interface KafkaProducerService {

    void send(String topic, Object data);
}
