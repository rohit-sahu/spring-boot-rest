package io.devzona.springboot.emailproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ppt")
public class Property implements java.io.Serializable {

    private static final long serialVersionUID = 3057116944905135154L;

    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "pptId", column = @Column(name = "PPT_ID", nullable = false)),
            @AttributeOverride(name = "pptName", column = @Column(name = "PPT_NAME", nullable = false, length = 64))})
    private PropertyId<Long> id;

    @Column
    @Nullable
    @CreatedBy
    private Long createdBy;

    @Column
    @Nullable
    @LastModifiedBy
    private Long lastModifiedBy;

    @Version
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer version;

    @Nullable
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Nullable
    @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = Boolean.FALSE;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "PPT_VALUE", length = 1024)
    private String pptValue;

    @Column(name = "EX_FIELD1")
    private Long exField1;

    @Column(name = "EX_FIELD2", length = 512)
    private String exField2;
}