package io.devzona.springboot.emailbatch;

import io.devzona.springboot.emailbatch.config.AuditorAwareImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.persistence.EntityManagerFactory;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(basePackages = "io.devzona.springboot.emailbatch.repository")
public class EmailBatchApplication {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		SpringApplication.run(EmailBatchApplication.class, args);
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
