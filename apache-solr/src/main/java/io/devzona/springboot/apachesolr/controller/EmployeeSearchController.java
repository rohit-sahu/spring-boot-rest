package io.devzona.springboot.apachesolr.controller;

import io.devzona.springboot.apachesolr.exception.RecordNotFoundException;
import io.devzona.springboot.apachesolr.model.Employee;
import io.devzona.springboot.apachesolr.model.EmployeeSearch;
import io.devzona.springboot.apachesolr.service.EmployeeSearchService;
import io.devzona.springboot.apachesolr.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Lazy
@RestController
@RequestMapping("/solr/employees")
public class EmployeeSearchController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployeeSearchService employeeSearchService;

    @PostConstruct
    public void storeEmployeeSearch() {
        List<Employee> list = service.getAllEmployees();
        List<EmployeeSearch> employeeSearchList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(list)) {
            for (Employee employee : list) {
                EmployeeSearch employeeSearch = new EmployeeSearch();
                BeanUtils.copyProperties(employee, employeeSearch);
                employeeSearch.setId(employee.getId());
                employeeSearchList.add(employeeSearch);
            }
        }
        employeeSearchService.saveAll(employeeSearchList);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeSearch>> getAllEmployees() {
        List<EmployeeSearch> list = employeeSearchService.getAllEmployees();

        return new ResponseEntity<List<EmployeeSearch>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeSearch> getEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        EmployeeSearch entity = employeeSearchService.getEmployeeById(id);

        return new ResponseEntity<EmployeeSearch>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeSearch> createOrUpdateEmployee(@RequestBody EmployeeSearch employee)
            throws RecordNotFoundException {
        EmployeeSearch updated = employeeSearchService.createOrUpdateEmployee(employee);
        return new ResponseEntity<EmployeeSearch>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        employeeSearchService.deleteEmployeeById(id);
        return HttpStatus.FORBIDDEN;
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeSearch>> searchEmployee(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName)
            throws RecordNotFoundException {
        Pageable page = PageRequest.of(0, 10);
        List<EmployeeSearch> entity = employeeSearchService.findByFirstNameAndLastName(firstName, lastName, page);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    /*@GetMapping("/search")
    public ResponseEntity<List<EmployeeSearch>> searchEmployee(@RequestParam("firstName") String firstName, @RequestParam("email") String email)
            throws RecordNotFoundException {
        Pageable page = PageRequest.of(0, 10);
        List<EmployeeSearch> entity = employeeSearchService.findByFirstNameOrEmail(firstName, email, page);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }*/

    /*@GetMapping("/search")
    public ResponseEntity<List<EmployeeSearch>> searchEmployee(@RequestParam("firstName") String firstName)
            throws RecordNotFoundException {
        Pageable page = PageRequest.of(0, 10);
        List<EmployeeSearch> entity = employeeSearchService.findByFirstNameAndFacetOnEmail(firstName, page);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }*/
}
