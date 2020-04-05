package io.devzona.springboot.apachesolr.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * This configuration is only for when you are working with custom solr queries using Solr Template.
 * <br></br>
 * <br></br>
 * <b>Note-</b>If you are working with spring data solr using annotation based or
 * {@link org.springframework.data.solr.repository.SolrCrudRepository SolrCrudRepository} then this config class or method is not required.
 *
 * @version 1.0
 * @since 31-03-2020
 * @author rohit-sahu
 * @see SolrTemplate
 * @see SolrClient
 * @see HttpSolrClient
 */
@Configuration
public class SolrConfig {

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder("http://localhost:8983/solr").build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}
