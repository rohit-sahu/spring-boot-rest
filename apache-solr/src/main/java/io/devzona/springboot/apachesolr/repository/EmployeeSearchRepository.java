package io.devzona.springboot.apachesolr.repository;

import io.devzona.springboot.apachesolr.model.EmployeeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSearchRepository extends SolrCrudRepository<EmployeeSearch, Long> {

    Page<EmployeeSearch> findByFirstName(String firstName, Pageable page);

    Page<EmployeeSearch> findByFirstNameAndLastName(String firstName, String lastName, Pageable page);

    Page<EmployeeSearch> findByFirstNameOrEmail(@Boost(2) String firstName, String email, Pageable page);

    @Query(value = "first_name:?0")
    @Facet(fields = { "email" }, limit=20)
    FacetPage<EmployeeSearch> findByFirstNameAndFacetOnEmail(String first_name, Pageable page);
}
