package io.devzona.springboot.emailconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

/**
 * Abstract base class for auditable entities. Stores the audition values in persistent fields.
 *
 * @author rohit-sahu
 * @param <U> the auditing type. Typically some kind of user.
 * @param <PK> the type of the audited type's identifier
 * @param <T> the type of the identifier.
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseAuditEntity<U extends Serializable, PK extends Serializable, T extends TemporalAccessor> implements Serializable {

    private static final long serialVersionUID = 662889236006783097L;

    private PK id;

    private U createdBy;

    private U lastModifiedBy;

    private Integer version;

    private T createdDate;

    private T lastModifiedDate;

    private Boolean isDeleted = Boolean.FALSE;

    private Boolean isActive = Boolean.TRUE;

}
