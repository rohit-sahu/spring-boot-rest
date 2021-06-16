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
public class Address extends BaseAuditEntity<String, Long, LocalDateTime> {

    private static final long serialVersionUID = -3164875065189787142L;

    private Integer houseNumber;

    private String street;

    private String landmark;

    private String city;

    private String state;

    private Zipcode zipcode;

    private String country;

    private AddressType addressType;

    private Employee employee;

    @Builder
    public Address(Long id, String createdBy, String lastModifiedBy, Integer version, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Boolean isDeleted,
                   Boolean isActive, Integer houseNumber, String street, String landmark, String city, String state, Zipcode zipcode, String country,
                   AddressType addressType, Employee employee) {
        super(id, createdBy, lastModifiedBy, version, createdDate, lastModifiedDate, isDeleted, isActive);
        this.houseNumber = houseNumber;
        this.street = street;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.addressType = addressType;
        this.employee = employee;
    }

    public enum AddressType {

        CURRENT("C"), PERMANENT("P");

        private String shortName;

        private AddressType(String shortName) {
            this.shortName = shortName;
        }

        public String getShortName() {
            return shortName;
        }

        public static AddressType fromShortName(String shortName) {
            switch (shortName) {
                case "C":
                    return AddressType.CURRENT;
                case "P":
                    return AddressType.PERMANENT;
                default:
                    throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
            }
        }
    }
}
