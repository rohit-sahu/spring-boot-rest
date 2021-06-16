package io.devzona.springboot.emailproducer.config;

import io.devzona.springboot.emailproducer.service.CryptoService;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Start Kafka by below command locally.
 *
 * kafka/bin/zookeeper-server-start.sh kafka/config/zookeeper.properties
 * kafka/bin/kafka-server-start.sh kafka/config/server.properties
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    private ProducerFactory<String, Object> producerFactory;

    @Autowired
    private CryptoService cryptoService;

    public Map<String, Object> producerConfig() {
        DefaultKafkaProducerFactory defaultKafkaProducerFactory = (DefaultKafkaProducerFactory) producerFactory;
        Map<String, Object> kafkaAutoConfig = defaultKafkaProducerFactory.getConfigurationProperties();
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.putAll(kafkaAutoConfig);
        producerConfig.put("request.required.acks", "0");
        producerConfig.put("use.latest.version", true);
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, 3);
        producerConfig.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerConfig.compute(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, (k, v) -> cryptoService.decrypt((String) v));
        producerConfig.compute(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, (k, v) -> cryptoService.decrypt((String) v));
        producerConfig.compute(SslConfigs.SSL_KEY_PASSWORD_CONFIG, (k, v) -> cryptoService.decrypt((String) v));
        return producerConfig;
    }

    //@Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public NewTopic topic1(@Value("${email.producer.test.topics.email}") String kafka_test_topic ) {
        return new NewTopic(kafka_test_topic, 1, (short) 1);
    }
}
