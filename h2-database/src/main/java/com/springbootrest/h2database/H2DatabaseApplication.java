package com.springbootrest.h2database;

import com.springbootrest.h2database.model.Employee;
import com.springbootrest.h2database.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
public class H2DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2DatabaseApplication.class, args);
	}

}
