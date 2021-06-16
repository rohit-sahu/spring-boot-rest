package io.devzona.springboot.emailproducer.controller;


import io.devzona.springboot.emailproducer.exception.RecordNotFoundException;
import io.devzona.springboot.emailproducer.model.Employee;
import io.devzona.springboot.emailproducer.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
public class EmployeeController {

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

    @PostMapping
    public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employee)
            throws RecordNotFoundException {
        Employee updated = service.createOrUpdateEmployee(employee);
        return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee)
            throws RecordNotFoundException {
        Employee updated = service.createOrUpdateEmployee(employee);
        return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        service.deleteEmployeeById(id);
        return HttpStatus.OK;
    }
}
