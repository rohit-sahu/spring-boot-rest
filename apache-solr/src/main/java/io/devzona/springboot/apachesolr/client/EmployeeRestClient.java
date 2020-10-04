package io.devzona.springboot.apachesolr.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devzona.springboot.apachesolr.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/rest/client/employees")
public class EmployeeRestClient {

    private static final String RESOURCE_PATH = "/employees";
    private final String REQUEST_URI;

    @Autowired
    private RestTemplate restTemplate;

    public EmployeeRestClient(@Value(value = "${server.host:http://localhost}") String host, @Value("${server.port}") int port) {
        this.REQUEST_URI = host + ":" + port + RESOURCE_PATH;
    }

    /**
     * Requests the employee resource for the specified id via HTTP GET using RestTemplate method getForEntity.
     *
     * @param id the id of the employee resource
     * @return a ResponseEntity that wraps http status code, http headers and the body of type {@link Employee}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        ResponseEntity<Employee> entity = restTemplate.getForEntity(REQUEST_URI + "/{id}",
                Employee.class,
                Long.toString(id));

        // log.info("Status code value: " + domain.getStatusCodeValue());
        // log.info("HTTP Header 'ContentType': " + domain.getHeaders().getContentType());

        return entity;
    }

    /**
     * Requests a specified amount of employee resources via HTTP GET using RestTemplate method getForEntity.
     * The amount is specified by the given page and pageSize parameter.
     *
     * @param page     the page
     * @param pageSize the amount of elements per page
     * @return a list of employees
     */
    public List<Employee> getAll(int page, int pageSize) {
        String requestUri = REQUEST_URI + "?page={page}&pageSize={pageSize}";

        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("page", Integer.toString(page));
        urlParameters.put("pageSize", Long.toString(pageSize));

        ResponseEntity<Employee[]> entity = restTemplate.getForEntity(requestUri,
                Employee[].class,
                urlParameters);

        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return restTemplate.exchange(REQUEST_URI, HttpMethod.GET, RequestEntity.get(URI.create(REQUEST_URI)).build(),
                new ParameterizedTypeReference<List<Employee>>(){});
    }

    /**
     * Requests the employee resource for the specified id via HTTP GET using RestTemplate method getForObject.
     *
     * @param id the id of the employee resource
     * @return the employee as {@link Optional} or an empty {@link Optional} if resource not found.
     */
    public Optional<Employee> getForObject(long id) {
        Employee employee = restTemplate.getForObject(REQUEST_URI + "/{id}",
                Employee.class,
                Long.toString(id));

        return Optional.ofNullable(employee);
    }

    /**
     * Requests the employee resource for the specified id via HTTP GET using RestTemplate method getForObject
     * and returns the resource as JsonNode.
     *
     * @param id the id of the employee resource
     * @return the employee resource as JsonNode
     * @throws IOException if received json string can not be parsed
     */
    public JsonNode getAsJsonNode(long id) throws IOException {
        String jsonString = restTemplate.getForObject(REQUEST_URI + "/{id}",
                String.class,
                id);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(jsonString);
    }

    /**
     * Creates an employee resource via HTTP POST using RestTemplate method getForObject.
     *
     * @param employee the employee to be created
     * @return the created employee
     */
    public Employee postForObject(Employee employee) {
        return restTemplate.postForObject(REQUEST_URI, employee, Employee.class);
    }

    /**
     * Creates an employee resource via HTTP POST using RestTemplate method getForLocation.
     *
     * @param employee the employee to be created
     * @return the {@link URI} of the created employee
     */
    public URI postForLocation(Employee employee) {
        return restTemplate.postForLocation(REQUEST_URI, new HttpEntity<>(employee));
    }

    /**
     * Creates an employee resource via HTTP POST using RestTemplate method postForEntity.
     *
     * @param newEmployee the employee to be created
     * @return a ResponseEntity that wraps http status code, http headers and the body of type {@link Employee}
     */
    @PostMapping
    public ResponseEntity<Employee> postForEntity(Employee newEmployee) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("User-Agent", "EmployeeRestClient demo class");
        headers.add("Accept-Language", "en-IN");

        HttpEntity<Employee> entity = new HttpEntity<>(newEmployee, headers);

        return restTemplate.postForEntity(REQUEST_URI, entity, Employee.class);
    }

    /**
     * Updates an employee resource via HTTP PUT using RestTemplate method put.
     *
     * @param updatedEmployee the employee to be updated
     */
    public void put(Employee updatedEmployee) {
        restTemplate.put(REQUEST_URI + "/{id}",
                updatedEmployee,
                Long.toString(updatedEmployee.getId()));
    }

    /**
     * Updates an employee resource via HTTP PUT using RestTemplate method exchange.
     *
     * @param updatedEmployee the employee to be updated
     * @return a ResponseEntity that wraps http status code, http headers and the body of type {@link Employee}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> putWithExchange(Employee updatedEmployee) {
        return restTemplate.exchange(REQUEST_URI + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updatedEmployee),
                Employee.class,
                Long.toString(updatedEmployee.getId()));
    }

    /**
     * Deletes an employee resurce via HTTP DELETE using RestTemplate method delete.
     *
     * @param id the id of the employee to be deleted
     */
    public void delete(long id) {
        restTemplate.delete(REQUEST_URI + "/{id}", Long.toString(id));
    }

    /**
     * Deletes an employee resource via HTTP DELETE using RestTemplate method exchange.
     *
     * @param id the id of the employee to be deleted
     * @return a ResponseEntity that wraps http status code and http headers
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWithExchange(@PathVariable("id") long id) {
        return restTemplate.exchange(REQUEST_URI + "/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                Long.toString(id));
    }

    /**
     * Requests the built request URI via HTTP HEAD.
     *
     * @return the HTTP headers for the requested URI.
     */
    public HttpHeaders headForHeaders() {
        return restTemplate.headForHeaders(REQUEST_URI);
    }

    /**
     * Requests the built request URI via HTTP OPTION.
     *
     * @param id the employee to be requested with OPTION
     * @return all allowed HTTP methods for the requested URI
     */
    public Set<HttpMethod> optionsForAllow(long id) {
        return restTemplate.optionsForAllow(REQUEST_URI + "/{id}", Long.toString(id));
    }

}
