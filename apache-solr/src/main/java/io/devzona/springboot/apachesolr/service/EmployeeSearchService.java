package io.devzona.springboot.apachesolr.service;

import io.devzona.springboot.apachesolr.exception.RecordNotFoundException;
import io.devzona.springboot.apachesolr.model.EmployeeSearch;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeSearchService {

    Iterable<EmployeeSearch> saveAll(Iterable<EmployeeSearch> employeeSearches);

    List<EmployeeSearch> getAllEmployees();

    EmployeeSearch getEmployeeById(Long id) throws RecordNotFoundException;

    EmployeeSearch createOrUpdateEmployee(EmployeeSearch entity) throws RecordNotFoundException;

    void deleteEmployeeById(Long id) throws RecordNotFoundException;

    List<EmployeeSearch> findByFirstName(String firstName, Pageable page) throws RecordNotFoundException;

    List<EmployeeSearch> findByFirstNameAndLastName(String firstName, String lastName, Pageable page) throws RecordNotFoundException;

    List<EmployeeSearch> findByFirstNameOrEmail(String firstName, String email, Pageable page) throws RecordNotFoundException;

    List<EmployeeSearch> findByFirstNameAndFacetOnEmail(String firstName, Pageable page) throws RecordNotFoundException;
}
