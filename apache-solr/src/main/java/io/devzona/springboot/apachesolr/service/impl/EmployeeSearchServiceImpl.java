package io.devzona.springboot.apachesolr.service.impl;

import io.devzona.springboot.apachesolr.exception.RecordNotFoundException;
import io.devzona.springboot.apachesolr.model.EmployeeSearch;
import io.devzona.springboot.apachesolr.repository.EmployeeSearchRepository;
import io.devzona.springboot.apachesolr.service.EmployeeSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeSearchServiceImpl implements EmployeeSearchService {

    @Autowired
    EmployeeSearchRepository repository;

    @Override
    public Iterable<EmployeeSearch> saveAll(Iterable<EmployeeSearch> employeeSearches) {
        return repository.saveAll(employeeSearches);
    }

    @Override
    public List<EmployeeSearch> getAllEmployees() {
        Page<EmployeeSearch> employeeSearchPage = (Page<EmployeeSearch>) repository.findAll();
        List<EmployeeSearch> employeeSearchList = employeeSearchPage.toList();
        if (employeeSearchList.size() > 0) {
            return employeeSearchList;
        } else {
            return new ArrayList<EmployeeSearch>();
        }
    }

    @Override
    public EmployeeSearch getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<EmployeeSearch> employee = repository.findById(id);

        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public EmployeeSearch createOrUpdateEmployee(EmployeeSearch entity) throws RecordNotFoundException {
        Optional<EmployeeSearch> employee = repository.findById(entity.getId());

        if (employee.isPresent()) {
            EmployeeSearch newEntity = employee.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());

            newEntity = repository.save(newEntity);

            return newEntity;
        } else {
            entity = repository.save(entity);

            return entity;
        }
    }

    @Override
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<EmployeeSearch> employee = repository.findById(id);

        if (employee.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public List<EmployeeSearch> findByFirstName(String firstName, Pageable page) throws RecordNotFoundException {
        Page<EmployeeSearch> employee = repository.findByFirstName(firstName, page);
        List<EmployeeSearch> employeeSearches = employee.toList();
        if (!ObjectUtils.isEmpty(employeeSearches)) {
            return employeeSearches;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public List<EmployeeSearch> findByFirstNameAndLastName(String firstName, String lastName, Pageable page) throws RecordNotFoundException {
        Page<EmployeeSearch> employee = repository.findByFirstNameAndLastName(firstName, lastName, page);
        List<EmployeeSearch> employeeSearches = employee.toList();
        if (!ObjectUtils.isEmpty(employeeSearches)) {
            return employeeSearches;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public List<EmployeeSearch> findByFirstNameOrEmail(String firstName, String email, Pageable page) throws RecordNotFoundException {
        Page<EmployeeSearch> employee = repository.findByFirstNameOrEmail(firstName, email, page);
        List<EmployeeSearch> employeeSearches = employee.toList();
        if (!ObjectUtils.isEmpty(employeeSearches)) {
            return employeeSearches;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public List<EmployeeSearch> findByFirstNameAndFacetOnEmail(String firstName, Pageable page) throws RecordNotFoundException {
        FacetPage<EmployeeSearch> employee = repository.findByFirstNameAndFacetOnEmail(firstName, page);
        List<EmployeeSearch> employeeSearches = employee.toList();
        employeeSearches.forEach(employeeSearch -> log.info(employeeSearch.toString()));
        if (!ObjectUtils.isEmpty(employeeSearches)) {
            return employeeSearches;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
