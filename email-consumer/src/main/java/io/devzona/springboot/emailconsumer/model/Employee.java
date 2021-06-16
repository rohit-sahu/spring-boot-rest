package io.devzona.springboot.emailconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Entity for the Employee class.
 *
 * @since 03-04-2020
 * @author rohit-sahu
 * @version 1.0
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseAuditEntity<String, Long, LocalDateTime> {

    private static final long serialVersionUID = -5510115603666184167L;

    private String firstName;

    private String lastName;

    private String email;

    private Set<ContactNumber> contactNumbers;

    private List<Address> address;

    @Builder
    public Employee(Long id, String createdBy, String lastModifiedBy, Integer version, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Boolean isDeleted,
                    Boolean isActive, String firstName, String lastName, String email, Set<ContactNumber> contactNumbers, List<Address> address) {
        super(id, createdBy, lastModifiedBy, version, createdDate, lastModifiedDate, isDeleted, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumbers = contactNumbers;
        this.address = address;
    }
}
