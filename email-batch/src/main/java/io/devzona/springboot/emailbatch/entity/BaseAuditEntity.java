package io.devzona.springboot.emailbatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.AccessType;
import javax.persistence.*;
import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

/**
 * Abstract base class for auditable entities. Stores the audition values in persistent fields.
 *
 * @author rohit-sahu
 * @param <U> the auditing type. Typically some kind of user.
 * @param <PK> the type of the audited type's identifier
 * @param <T> the type of the identifier.
 * @see org.springframework.data.domain.Auditable
 * @see org.springframework.data.domain.Persistable
 * @see org.springframework.data.jpa.domain.AbstractAuditable
 * @see org.springframework.data.jpa.domain.AbstractPersistable
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity<U extends Serializable, PK extends Serializable, T extends TemporalAccessor> implements Serializable {

    private static final long serialVersionUID = 662889236006783097L;

    @Id
    @OrderBy(clause = "id ASC")
    @Access(value = AccessType.FIELD)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @Column
    @Nullable
    @CreatedBy
    private U createdBy;

    @Column
    @Nullable
    @LastModifiedBy
    private U lastModifiedBy;

    @Version
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer version;

    @Nullable
    @CreatedDate
    @CreationTimestamp
    private T createdDate;

    @Nullable
    @LastModifiedDate
    @UpdateTimestamp
    private T lastModifiedDate;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = Boolean.FALSE;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive = Boolean.TRUE;

}
