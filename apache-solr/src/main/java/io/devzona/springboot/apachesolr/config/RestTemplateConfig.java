package io.devzona.springboot.apachesolr.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Autowired
    private final CloseableHttpClient httpClient;

    @Autowired
    public RestTemplateConfig(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Bean
    public RestTemplate restTemplate() {
        log.info("RestTemplate is configuring with {}", httpClient);
        return new RestTemplateBuilder()
                .requestFactory(this::clientHttpRequestFactory)
                .errorHandler(new CustomClientErrorHandler())
                .interceptors(new CustomClientHttpRequestInterceptor())
                .build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }
}