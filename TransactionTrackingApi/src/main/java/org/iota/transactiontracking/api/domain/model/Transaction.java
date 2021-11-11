package org.iota.transactiontracking.api.domain.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Entity for the transaction domain object
 */
@Entity
@Data
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private Long value;

    private Long timestamp;

    private String receiver;

    private boolean confirmed;

    private String sender;
}
