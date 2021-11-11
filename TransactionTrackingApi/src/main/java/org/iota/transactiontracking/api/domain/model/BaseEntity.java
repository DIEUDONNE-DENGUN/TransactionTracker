package org.iota.transactiontracking.api.domain.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Base Enity to do prepersist and postpersist actions on the db
 */
@Data
@MappedSuperclass
public class BaseEntity {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createdAt = localDateTime;
        this.updatedAt = localDateTime;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
