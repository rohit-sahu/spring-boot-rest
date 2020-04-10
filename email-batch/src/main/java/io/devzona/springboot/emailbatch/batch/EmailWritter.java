package io.devzona.springboot.emailbatch.batch;

import io.devzona.springboot.emailbatch.entity.Employee;
import io.devzona.springboot.emailbatch.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmailWritter implements ItemWriter<Employee> {

    @Autowired
    private EmailService emailService;

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        log.info("All Employees is {}", items.toString());
        items.forEach(System.out::println);
        items.forEach(employee -> {
            emailService.sendMail("rohit.kumar6@magicbricks.com", "Test", "Hello test");
        });
    }
}
