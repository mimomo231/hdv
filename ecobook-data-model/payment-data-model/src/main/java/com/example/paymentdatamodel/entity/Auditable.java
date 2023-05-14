package com.example.paymentdatamodel.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter @Setter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class Auditable<U> {

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private U createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private U updatedBy;
}