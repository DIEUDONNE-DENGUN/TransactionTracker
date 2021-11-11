package org.iota.transactiontracking.api.service;

import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.iota.transactiontracking.api.response.SimpleTransactionResponse;
import org.iota.transactiontracking.api.response.TransactionCollectionResponse;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Service interface to handle all transaction crud operations into the persistent layer
 */
public interface TransactionService {

    SimpleTransactionResponse saveTransaction(TransactionRequest transactionRequest);

    void updateTransaction(String transactionId, TransactionUpdateRequest transactionUpdateRequest);

    SimpleTransactionResponse getTransactionById(String transactionId);

    TransactionCollectionResponse getTransactions();

    TransactionCollectionResponse getTransactionsByDateRange(String startDate, String endDate);

    TransactionCollectionResponse searchTransactionsByIdOrValue(String transactionId, Long value);
}
