package org.iota.transactiontracking.api.response;

import lombok.Data;

import java.util.List;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class TransactionCollectionResponse {
    private List<SimpleTransactionResponse> transactionResource;
}
