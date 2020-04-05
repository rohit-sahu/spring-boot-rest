package io.devzona.springboot.apachesolr.repository.impl;

import io.devzona.springboot.apachesolr.model.EmployeeSearch;
import io.devzona.springboot.apachesolr.repository.EmployeeSearchCustomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class EmployeeSearchCustomRepositoryImpl extends GenericRepositoryImpl<EmployeeSearch, Long> implements EmployeeSearchCustomRepository {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Page<EmployeeSearch> findByFirstName(String firstName, Pageable page) {
        return null;
    }

    @Override
    public Page<EmployeeSearch> findByFirstNameAndLastName(String firstName, String lastName, Pageable page) {
        return null;
    }

    @Override
    public Page<EmployeeSearch> findByFirstNameOrEmail(String firstName, String email, Pageable page) {
        return null;
    }

    @Override
    public FacetPage<EmployeeSearch> findByFirstNameAndFacetOnEmail(String first_name, Pageable page) {
        return null;
    }
}
