package io.devzona.springboot.emailproducer.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CND")
public class CommonData extends BaseAuditEntity<String, Long, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    @Column(name = "CNDCODE", length = 6, nullable = false)
    private String cndcode;

    @Column(name = "CNDDESC", length = 512, nullable = false)
    private String cnddesc;

    @Column(name = "CNDCNDRFNUM", length = 512, nullable = false)
    private Long cndcndrfnum;

    @Column(name = "CNDFIELDTYPE", length = 2)
    private String cndfieldtype;

    @Column(name = "CNDGROUP", length = 128, nullable = false)
    private String cndgroup;

    @Column(name = "CNDSEQNUM", length = 53, nullable = false)
    private Double cndseqnum;

    @Column(name = "EXFIELD1", length = 19)
    private Long exfield1;

    @Column(name = "EXFIELD2", length = 512)
    private String exfield2;

    @Builder
    public CommonData(Long id, String createdBy, String lastModifiedBy, Integer version, LocalDateTime createdDate,
                      LocalDateTime lastModifiedDate, Boolean isDeleted, Boolean isActive, String cndcode, String cnddesc,
                      Long cndcndrfnum, String cndfieldtype, String cndgroup, Double cndseqnum, Long exfield1, String exfield2) {
        super(id, createdBy, lastModifiedBy, version, createdDate, lastModifiedDate, isDeleted, isActive);
        this.cndcode = cndcode;
        this.cnddesc = cnddesc;
        this.cndcndrfnum = cndcndrfnum;
        this.cndfieldtype = cndfieldtype;
        this.cndgroup = cndgroup;
        this.cndseqnum = cndseqnum;
        this.exfield1 = exfield1;
        this.exfield2 = exfield2;
    }
}
