package org.iota.transactiontracking.unit;

import org.iota.transactiontracking.api.controller.TransactionController;
import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.iota.transactiontracking.api.response.SimpleTransactionResponse;
import org.iota.transactiontracking.api.response.TransactionCollectionResponse;
import org.iota.transactiontracking.api.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

/*
   @Author:Dieudonne Takougang
   @Date: 17/10/2021
   @Description: Unit Test cases for the different endpoints for the transaction controller
 */
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService mockTransactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void createTransaction_calls_TransactionService() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setValue(100L);
        transactionRequest.setReceiver("Dieudonne");
        transactionRequest.setSender("Takougang");
        transactionRequest.setTimestamp(1634473449593L);
        transactionRequest.setConfirmed(Boolean.FALSE);
        ResponseEntity<SimpleTransactionResponse> transaction = transactionController.createTransaction(transactionRequest);
        verify(mockTransactionService).saveTransaction(transactionRequest);
        assertThat(transaction.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateTransaction_calls_TransactionService() {
        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setValue(2000L);
        transactionUpdateRequest.setValue(1634473449594L);
        transactionUpdateRequest.setReceiver("updatedReceiver");
        transactionUpdateRequest.setSender("updatedSender");
        ResponseEntity<Void> transaction = transactionController.updateTransaction("acdb4-habna", transactionUpdateRequest);
        verify(mockTransactionService).updateTransaction("acdb4-habna", transactionUpdateRequest);
        assertThat(transaction.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getTransaction_calls_TransactionService() {
        ResponseEntity<SimpleTransactionResponse> transactionResponse = transactionController.getTransactionById("acdb4-habna");
        verify(mockTransactionService).getTransactionById("acdb4-habna");
        assertThat(transactionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getTransactions_calls_TransactionService() {
        ResponseEntity<TransactionCollectionResponse> transactionResponse = transactionController.getTransactions("", "");
        verify(mockTransactionService).getTransactions();
        assertThat(transactionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void searchTransaction_calls_TransactionService() {
        ResponseEntity<TransactionCollectionResponse> transactionResponse = transactionController.searchTransactionByIdOrValue("acdb4-habna", 0L);
        verify(mockTransactionService).searchTransactionsByIdOrValue("acdb4-habna", 0L);
        assertThat(transactionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
