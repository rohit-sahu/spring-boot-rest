package io.devzona.springboot.emailproducer.repository;


import io.devzona.springboot.emailproducer.model.Property;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query("Select pptValue from Property where id.pptName = :pptname and isDeleted = false")
    List<String> findppptValueByNameRfnum(@Param("pptname") String pptName);

    @Query("Select pptValue from Property where id.pptName IN :pptname and isDeleted = false")
    List<String> findByPptnameIn(@Param("pptname") Iterable<String> pptName);

    @Query("Select pptValue from Property")
    List<String> healthCheckQuery(Pageable pageable);
}
