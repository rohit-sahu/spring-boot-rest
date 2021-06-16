package io.devzona.springboot.emailconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ContactNumber extends BaseAuditEntity<String, Long, LocalDateTime> {

    private String areaCode;

    private ContactType contactType;

    private String subscriberNumber;

    private String countryCode;

    private String serviceProvider;

    private Employee employee;

    @Builder
    public ContactNumber(Long id, String createdBy, String lastModifiedBy, Integer version, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Boolean isDeleted,
                    Boolean isActive, String areaCode, ContactType contactType, String subscriberNumber, String countryCode, String serviceProvider) {
        super(id, createdBy, lastModifiedBy, version, createdDate, lastModifiedDate, isDeleted, isActive);
        this.areaCode = areaCode;
        this.contactType = contactType;
        this.subscriberNumber = subscriberNumber;
        this.countryCode = countryCode;
        this.serviceProvider = serviceProvider;
    }

    public enum ContactType {
        MOBILE, LANDLINE
    }
}
