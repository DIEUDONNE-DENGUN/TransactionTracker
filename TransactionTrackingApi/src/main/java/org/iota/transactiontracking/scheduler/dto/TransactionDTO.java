package org.iota.transactiontracking.scheduler.dto;


import lombok.Data;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class TransactionDTO {

    private Long value;
    private Long timestamp;
    private String receiver;
    private String sender;
    private Boolean confirmed;
}
