package org.iota.transactiontracking.api.response;
/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */

import lombok.Data;

@Data
public class SimpleTransactionResponse {
    private String transactionId;
    private Long transactionValue;
    private String transactionDate;
    private String transactionReceiver;
    private String transactionSender;
    private Boolean transactionStatus;

}
