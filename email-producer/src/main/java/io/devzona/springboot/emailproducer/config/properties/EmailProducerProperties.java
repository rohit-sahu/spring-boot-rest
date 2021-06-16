package io.devzona.springboot.emailproducer.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Email Producer custom properties configurations.
 *
 * @author Rohit.Sahu
 */
@Data
@Component
@ConfigurationProperties(prefix = "email.producer")
public class EmailProducerProperties {

    @NotNull
    @NotEmpty
    private SystemConfig systemConfig;

    @NotBlank
    private String servers;

    @NotBlank
    private String serializerKey;

    @NotBlank
    private String serializerValue;

    @NotNull
    @NotEmpty
    private Map<String, String> topics;

    @Data
    public static class SystemConfig {

        @NotEmpty
        private String domain;

        @NotEmpty
        private String tinyDomain;

        @NotEmpty
        private String smsShortUrlEnv;
    }
}
