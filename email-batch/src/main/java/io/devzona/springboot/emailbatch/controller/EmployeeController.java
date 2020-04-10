package io.devzona.springboot.emailbatch.controller;

import io.devzona.springboot.emailbatch.entity.Employee;
import io.devzona.springboot.emailbatch.exception.RecordNotFoundException;
import io.devzona.springboot.emailbatch.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    @Qualifier("demoJob")
    Job job;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private EmployeeService service;

    @PostConstruct
    public void storeEmployee() throws RecordNotFoundException {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder().firstName("Rohit").lastName("Raj").email("r@gmail.com").isActive(Boolean.TRUE).isDeleted(Boolean.FALSE).build());
        employees.add(Employee.builder().firstName("Mohit").lastName("Raj").email("m@gmail.com").isActive(Boolean.TRUE).isDeleted(Boolean.FALSE).build());
        employees.add(Employee.builder().firstName("Bittu").lastName("Raj").email("b@gmail.com").isActive(Boolean.TRUE).isDeleted(Boolean.FALSE).build());
        employees.add(Employee.builder().firstName("Prem").lastName("Raj").email("p@gmail.com").isActive(Boolean.TRUE).isDeleted(Boolean.FALSE).build());
        employees.add(Employee.builder().firstName("Kishan").lastName("Raj").email("k@gmail.com").isActive(Boolean.TRUE).isDeleted(Boolean.FALSE).build());
        service.createOrUpdateEmployee(employees);
    }

    @GetMapping("/send-email")
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        log.info("JobExecution: {} ", jobExecution.getStatus());
        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
        return jobExecution.getStatus();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> list = service.getAllEmployees();

        return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        Employee entity = service.getEmployeeById(id);

        return new ResponseEntity<Employee>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employee)
            throws RecordNotFoundException {
        Employee updated = service.createOrUpdateEmployee(employee);
        return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        service.deleteEmployeeById(id);
        return HttpStatus.FORBIDDEN;
    }
}
