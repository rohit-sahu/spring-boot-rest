package io.devzona.springboot.apachesolr.model;

import io.devzona.springboot.apachesolr.utils.AddressTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Slf4j
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address extends BaseAuditEntity<String, Long, LocalDateTime> {

    private static final long serialVersionUID = -3164875065189787142L;

    @Column(name = "ADDRESS_HOUSE_NUMBER", nullable = true, length=10)
    private Integer houseNumber;

    @Column(name = "ADDRESS_STREET", nullable = true, length=250)
    private String street;

    @Column(name = "ADDRESS_LANDMARK", nullable = true, length=100)
    private String landmark;

    @Column(name = "ADDRESS_CITY", nullable = true, length=50)
    private String city;

    @Column(name = "ADDRESS_STATE", nullable = true, length=50)
    private String state;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "zip", column = @Column(name = "PIN_CODE")) })
    private Zipcode zipcode;

    @Column(name = "ADDRESS_COUNTRY", nullable = true, length=50)
    private String country;

    @Column(length = 10)
    @Convert(converter = AddressTypeConverter.class)
    private AddressType addressType;

    @JoinColumn(name = "employee_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
