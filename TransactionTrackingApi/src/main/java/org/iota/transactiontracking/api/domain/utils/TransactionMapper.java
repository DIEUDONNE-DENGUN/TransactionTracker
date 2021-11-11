package org.iota.transactiontracking.api.domain.utils;

import org.iota.transactiontracking.api.domain.model.Transaction;
import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.iota.transactiontracking.api.response.SimpleTransactionResponse;
import org.iota.transactiontracking.api.response.TransactionCollectionResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: map entity to dto responses and vice versa between different application layers
 */
public class TransactionMapper {

    public static Transaction mapToTransactionEntity(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setValue(transactionRequest.getValue());
        transaction.setReceiver(transactionRequest.getReceiver());
        transaction.setSender(transactionRequest.getSender());
        transaction.setTimestamp(transactionRequest.getTimestamp());
        transaction.setConfirmed(Boolean.FALSE);
        return transaction;
    }

    public static Transaction mapToTransactionEntityUpdate(TransactionUpdateRequest transactionUpdateRequest, Transaction transaction) {
        transaction.setReceiver(transactionUpdateRequest.getReceiver());
        transaction.setValue(transactionUpdateRequest.getValue());
        transaction.setSender(transactionUpdateRequest.getSender());
        transaction.setConfirmed( transactionUpdateRequest.isConfirmed());
        return transaction;
    }

    public static SimpleTransactionResponse mapToTransactionResponse(Transaction transaction) {
        SimpleTransactionResponse transactionResponseDTO = new SimpleTransactionResponse();
        transactionResponseDTO.setTransactionId(transaction.getId());
        transactionResponseDTO.setTransactionValue(transaction.getValue());
        transactionResponseDTO.setTransactionDate(convertTimeStampDate(transaction.getTimestamp()));
        transactionResponseDTO.setTransactionReceiver(transaction.getReceiver());
        transactionResponseDTO.setTransactionSender(transaction.getSender());
        transactionResponseDTO.setTransactionStatus(transaction.isConfirmed());
        return transactionResponseDTO;
    }

    public static TransactionCollectionResponse mapToTransactionCollectionResponse(List<Transaction> transactionList) {
        List<SimpleTransactionResponse> simpleTransactionResponses = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            simpleTransactionResponses.add(mapToTransactionResponse(transaction));
        }
        TransactionCollectionResponse response = new TransactionCollectionResponse();
        response.setTransactionResource(simpleTransactionResponses);
        return response;
    }

    private static String convertTimeStampDate(Long timestamp) {
        return new Date(timestamp).toString();
    }
}
