package io.devzona.springboot.emailconsumer.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devzona.springboot.emailconsumer.model.Employee;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;

    public Map<String, Object> consumerConfig() {
        Map<String, Object> kafkaAutoConfig = kafkaProperties.buildConsumerProperties();
        //Map<String, Object> kafkaAutoConfig = consumerFactory.getConfigurationProperties();
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.putAll(kafkaAutoConfig);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        consumerConfig.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //consumerConfig.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
        consumerConfig.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerConfig.compute(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, (k, v) -> v);
        consumerConfig.compute(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, (k, v) -> v);
        consumerConfig.compute(SslConfigs.SSL_KEY_PASSWORD_CONFIG, (k, v) -> v);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return consumerConfig;
    }

    @Bean
    public ConsumerFactory<String, List<Employee>> employeeConsumerFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, Employee.class);
        DefaultKafkaConsumerFactory<String, List<Employee>> defaultKafkaConsumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                        new JsonDeserializer(type, objectMapper, false));
        return defaultKafkaConsumerFactory;
    }

    /**
     * This method returns a new ConcurrentKafkaListenerContainerFactory object.
     *
     * @return ConcurrentKafkaListenerContainerFactory object
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<Employee>> employeeKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<Employee>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(employeeConsumerFactory());
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setConcurrency(10);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setConcurrency(10);
        return factory;
    }

    // String Consumer Configuration
    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setConcurrency(10);
        return factory;
    }

    // Byte Array Consumer Configuration
    @Bean
    public ConsumerFactory<String, byte[]> byteArrayConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ByteArrayDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerByteArrayContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(byteArrayConsumerFactory());
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setConcurrency(10);
        return factory;
    }
}
