package io.devzona.springboot.apachesolr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Zipcode {

    @Column(nullable = true, length=10)
    private String zip;

    @Column(nullable = true, length=10)
    private String plusFour;
}
