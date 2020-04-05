package io.devzona.springboot.apachesolr.repository;

import io.devzona.springboot.apachesolr.model.EmployeeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSearchCustomRepository extends GenericRepository<EmployeeSearch, Long> {

    Page<EmployeeSearch> findByFirstName(String firstName, Pageable page);

    Page<EmployeeSearch> findByFirstNameAndLastName(String firstName, String lastName, Pageable page);

    Page<EmployeeSearch> findByFirstNameOrEmail(String firstName, String email, Pageable page);

    FacetPage<EmployeeSearch> findByFirstNameAndFacetOnEmail(String first_name, Pageable page);
}
