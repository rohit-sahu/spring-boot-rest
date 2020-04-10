package io.devzona.springboot.emailbatch.batch;

import io.devzona.springboot.emailbatch.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) throws Exception {
        log.info("Write code");
        employee.setEmail("rohit.kumar6@magicbricks.com");
        log.info("Employee is processed - {}", employee.toString());
        return employee;
    }
}
