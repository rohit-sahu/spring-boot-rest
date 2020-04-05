package io.devzona.springboot.apachesolr.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;

@Slf4j
@Data
@SolrDocument(solrCoreName = "employee", collection = "employee")
public class EmployeeSearch implements Serializable {

    @Id
    @Indexed
    private Long id;

    @Indexed(name = "first_name", type = "string")
    private String firstName;

    @Indexed(name = "last_name", type = "string")
    private String lastName;

    @Indexed(name = "email", type = "string")
    private String email;
}
