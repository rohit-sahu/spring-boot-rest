package io.devzona.springboot.emailproducer.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "LMT")
public class Lmt extends BaseAuditEntity<String, Long, LocalDateTime> {

    private static final long serialVersionUID = 6462916855109774459L;

    @Column(name = "LMTNAME", nullable = false, length = 128)
    private String lmtname;

    @Column(name = "LMTNEARBYRFNUM", length = 512)
    private String lmtnearbyrfnum;

    @Column(name = "LONGITUDE", precision = 53, scale = 0)
    private Double longitude;

    @Column(name = "LATITUDE", precision = 53, scale = 0)
    private Double latitude;

    @Column(name = "LMTPOPULAR")
    private Long lmtpopular;

    @Column(name = "LMTCNDCITY")
    private Long lmtcndcity;

    @Column(name = "LMTCNDZIP")
    private Long lmtcndzip;

    @Column(name = "LMTLMTRFNUM")
    private Long lmtlmtrfnum;

    @Column(name = "LMTDISPNAME", nullable = false, length = 128)
    private String lmtdispname;

    @Column(name = "LMTDISPLAYID")
    private Long lmtdisplayid;

    @Column(name = "lmtseoname")
    private String lmtseoname;

    @Column(name = "LMTALIAS1")
    private String lmtalias;
    private Long exfield1;

    @Builder
    public Lmt(Long id, String createdBy, String lastModifiedBy, Integer version, LocalDateTime createdDate,
               LocalDateTime lastModifiedDate, Boolean isDeleted, Boolean isActive, String lmtname, String lmtnearbyrfnum,
               Double longitude, Double latitude, Long lmtpopular, Long lmtcndcity, Long lmtcndzip, Long lmtlmtrfnum, String lmtdispname,
               Long lmtdisplayid, String lmtseoname, String lmtalias, Long exfield1) {
        super(id, createdBy, lastModifiedBy, version, createdDate, lastModifiedDate, isDeleted, isActive);
        this.lmtname = lmtname;
        this.lmtnearbyrfnum = lmtnearbyrfnum;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lmtpopular = lmtpopular;
        this.lmtcndcity = lmtcndcity;
        this.lmtcndzip = lmtcndzip;
        this.lmtlmtrfnum = lmtlmtrfnum;
        this.lmtdispname = lmtdispname;
        this.lmtdisplayid = lmtdisplayid;
        this.lmtseoname = lmtseoname;
        this.lmtalias = lmtalias;
        this.exfield1 = exfield1;
    }
}