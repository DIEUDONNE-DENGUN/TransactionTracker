package org.iota.transactiontracking.scheduler.dto;

import lombok.Data;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class TransactionUpdateDTO {
    private Long value;
    private String receiver;
    private String sender;
    private Boolean confirmed;
}
