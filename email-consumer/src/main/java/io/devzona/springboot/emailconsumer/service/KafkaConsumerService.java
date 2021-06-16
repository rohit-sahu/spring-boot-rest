package io.devzona.springboot.emailconsumer.service;

import io.devzona.springboot.emailconsumer.model.Employee;
import org.springframework.messaging.MessageHeaders;

import java.util.List;

public interface KafkaConsumerService {

    void receive(MessageHeaders headers, List<Employee> data);
}
