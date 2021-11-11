package org.iota.transactiontracking.scheduler.dto;

import lombok.Data;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class TransactionResponse {
    private String transactionId;
    private Long transactionValue;
    private String transactionDate;
    private String transactionReceiver;
    private String transactionSender;
    private Boolean transactionStatus;
}
