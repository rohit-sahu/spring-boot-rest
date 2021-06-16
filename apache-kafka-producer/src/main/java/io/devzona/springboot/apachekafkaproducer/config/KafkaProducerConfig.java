package io.devzona.springboot.apachekafkaproducer.config;

import io.devzona.springboot.apachekafkaproducer.service.CryptoService;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private ProducerFactory<Integer, String> producerFactory;

    @Autowired
    private CryptoService cryptoService;

    public Map<String, Object> producerConfig(){
        DefaultKafkaProducerFactory defaultKafkaProducerFactory = (DefaultKafkaProducerFactory) producerFactory;
        Map<String, Object> kafkaAutoConfig = defaultKafkaProducerFactory.getConfigurationProperties();
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.putAll(kafkaAutoConfig);
        producerConfig.compute(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, (k,v) -> cryptoService.decrypt((String)v));
        producerConfig.compute(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, (k, v) -> cryptoService.decrypt((String)v));
        producerConfig.compute(SslConfigs.SSL_KEY_PASSWORD_CONFIG, (k,v) -> cryptoService.decrypt((String)v));
        return producerConfig;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
    }
}
