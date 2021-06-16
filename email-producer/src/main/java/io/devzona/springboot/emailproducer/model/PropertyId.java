package io.devzona.springboot.emailproducer.model;

import lombok.Data;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class PropertyId<PK extends Serializable> implements java.io.Serializable {

    private static final long serialVersionUID = 1460621524139184401L;

    @OrderBy(clause = "PPT_ID ASC")
    @Access(value = AccessType.FIELD)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPT_ID", unique = true, nullable = false)
    private PK pptId;

    @Column(name = "PPT_NAME", nullable = false, length = 64)
    private String pptName;

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof PropertyId))
            return false;
        PropertyId castOther = (PropertyId) other;

        return ((this.getPptId() == castOther.getPptId()) ||
                (this.getPptId() != null && castOther.getPptId() != null && this.getPptId().equals(castOther.getPptId()))) &&
                ((this.getPptName() == castOther.getPptName()) || (this.getPptName() != null && castOther.getPptName() != null &&
                        this.getPptName().equals(castOther.getPptName())));
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (getPptId() == null ? 0 : this.getPptId().hashCode());
        result = 37 * result + (getPptName() == null ? 0 : this.getPptName().hashCode());
        return result;
    }
}