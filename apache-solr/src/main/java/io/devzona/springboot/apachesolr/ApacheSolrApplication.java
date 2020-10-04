package io.devzona.springboot.apachesolr;

import io.devzona.springboot.apachesolr.config.AuditorAwareImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.persistence.EntityManagerFactory;

@Slf4j
@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
@EnableJpaRepositories(basePackages = "io.devzona.springboot.apachesolr.repository")
@EnableSolrRepositories(basePackages = "io.devzona.springboot.apachesolr.repository")
public class ApacheSolrApplication {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        SpringApplication.run(ApacheSolrApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }

    @Bean
    public HibernateTemplate hibernateTemplate() {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(entityManagerFactory.unwrap(SessionFactory.class));
        hibernateTemplate.setCheckWriteOperations(false);
        return hibernateTemplate;
    }

}
