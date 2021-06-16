package io.devzona.springboot.emailproducer.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "mvt", uniqueConstraints = @UniqueConstraint(columnNames = {"MVTMOBILE", "MVTMOBILEISD"}))
public class MobileVerification extends BaseAuditEntity<String, Long, LocalDateTime> {

    @Column(name = "MVTMOBILE", nullable = false)
    private Long mvtmobile;
    @Column(name = "MVTMOBILEISD", nullable = false)
    private Short mvtmobileisd;
    @Column(name = "MVTCNDSTATUS", nullable = false)
    private Long mvtcndstatus;
    @Column(name = "MVTEMAIL", nullable = false, length = 64)
    private String mvtemail;
    @Column(name = "MVTATTEMPT1", nullable = false, length = 0)
    private Date mvtattempt1;
    @Column(name = "MVTATTEMPT2", nullable = false, length = 0)
    private Date mvtattempt2;
    @Column(name = "MVTATTEMPT3", nullable = false, length = 0)
    private Date mvtattempt3;
    @Column(name = "MVTENDDATE", length = 0)
    private Date mvtenddate;
    @Column(name = "ATTEMPTCOUNT", nullable = false)
    private Integer attemptcount;
    @Column(name = "MVTSOURCE", nullable = false, length = 20)
    private String mvtsource;
    @Column(name = "EXFIELD1", precision = 22, scale = 0)
    private Double exfield1;
    @Column(name = "EXFIELD2", length = 512)
    private String exfield2;
    @Column(name = "MVTCOOKIEVERIFIED", length = 1)
    private String mvtcookieverified;
    @Column(name = "MVTOTPUSED", length = 1)
    private String mvtotpused;
    @Column(name = "MVTUNIQID", length = 512)
    private String mvtuniqid;

    @Builder
    public MobileVerification(Long id, String createdBy, String lastModifiedBy, Integer version, LocalDateTime createdDate,
                              LocalDateTime lastModifiedDate, Boolean isDeleted, Boolean isActive, Long mvtmobile, Short mvtmobileisd,
                              Long mvtcndstatus, String mvtemail, Date mvtattempt1, Date mvtattempt2, Date mvtattempt3, Date mvtenddate,
                              Integer attemptcount, String mvtsource, Double exfield1, String exfield2, String mvtcookieverified,
                              String mvtotpused, String mvtuniqid) {
        super(id, createdBy, lastModifiedBy, version, createdDate, lastModifiedDate, isDeleted, isActive);
        this.mvtmobile = mvtmobile;
        this.mvtmobileisd = mvtmobileisd;
        this.mvtcndstatus = mvtcndstatus;
        this.mvtemail = mvtemail;
        this.mvtattempt1 = mvtattempt1;
        this.mvtattempt2 = mvtattempt2;
        this.mvtattempt3 = mvtattempt3;
        this.mvtenddate = mvtenddate;
        this.attemptcount = attemptcount;
        this.mvtsource = mvtsource;
        this.exfield1 = exfield1;
        this.exfield2 = exfield2;
        this.mvtcookieverified = mvtcookieverified;
        this.mvtotpused = mvtotpused;
        this.mvtuniqid = mvtuniqid;
    }
}