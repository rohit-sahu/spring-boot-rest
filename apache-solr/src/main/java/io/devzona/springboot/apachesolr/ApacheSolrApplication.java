package io.devzona.springboot.apachesolr;

import io.devzona.springboot.apachesolr.config.AuditorAwareImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Slf4j
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@EnableAutoConfiguration
//@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
@EnableJpaRepositories(basePackages = "io.devzona.springboot.apachesolr.repository")
@EnableSolrRepositories(basePackages = "io.devzona.springboot.apachesolr.repository")
public class ApacheSolrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApacheSolrApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }

}
