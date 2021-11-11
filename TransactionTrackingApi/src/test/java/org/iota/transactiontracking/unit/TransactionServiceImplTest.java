package org.iota.transactiontracking.unit;

import org.iota.transactiontracking.api.domain.exception.TransactionNotFoundException;
import org.iota.transactiontracking.api.domain.exception.utils.ErrorMessage;
import org.iota.transactiontracking.api.domain.model.Transaction;
import org.iota.transactiontracking.api.domain.repository.TransactionRepository;
import org.iota.transactiontracking.api.domain.service.TransactionServiceImpl;
import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.iota.transactiontracking.api.response.SimpleTransactionResponse;
import org.iota.transactiontracking.api.response.TransactionCollectionResponse;
import org.iota.transactiontracking.api.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
  @Author:Dieudonne Dengun
  @Date: 17/10/2021
  @Description: describe the unit test cases for the transaction implementation service methods
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository mockTransactionRepository;

    private TransactionService transactionService;
    private Transaction transaction;
    private ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(mockTransactionRepository);
        //create a dummy transaction entity for later use
        transaction = new Transaction();
        transaction.setId("acdb4-habna-2222-uushs");
        transaction.setTimestamp(1634473449593L);

    }

    @Test
    void createTransaction_should_create_new_transaction() {

        //create a simple transaction dto
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setConfirmed(false);
        transactionRequest.setReceiver("dieudonne");
        transactionRequest.setSender("takougang");
        transactionRequest.setTimestamp(1634473449593L);
        transactionRequest.setValue(10000L);
        //set mock data for a test transaction
        when(mockTransactionRepository.save(any())).thenReturn(transaction);
        transactionService.saveTransaction(transactionRequest);
        //assert if transaction request details can be saved by the mock repository
        verify(mockTransactionRepository).save(transactionArgumentCaptor.capture());
        assertThat(transactionArgumentCaptor.getValue().getValue()).isEqualTo(10000L);
        assertThat(transactionArgumentCaptor.getValue().getSender()).isEqualTo("takougang");
        assertThat(transactionArgumentCaptor.getValue().getReceiver()).isEqualTo("dieudonne");
        assertThat(transactionArgumentCaptor.getValue().getTimestamp()).isEqualTo(1634473449593L);
    }

    @Test
    void updateTransaction_should_update_transaction() {
        String transactionId = "536dca89-81dc-46c8-91d4-6e59595caa79";
        //create update transaction request dto
        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setConfirmed(Boolean.TRUE);
        transactionUpdateRequest.setReceiver("UpdatedReceiver");
        transactionUpdateRequest.setSender("UpdatedSender");
        transactionUpdateRequest.setValue(20000L);

        when(mockTransactionRepository.findById(transactionId)).thenReturn(Optional.of(new Transaction()));
        transactionService.updateTransaction(transactionId, transactionUpdateRequest);
        verify(mockTransactionRepository).save(transactionArgumentCaptor.capture());
        verify(mockTransactionRepository).findById(transactionId);
        //assert transaction update params
        assertThat(transactionArgumentCaptor.getValue().getValue()).isEqualTo(20000L);
        assertThat(transactionArgumentCaptor.getValue().getReceiver()).isEqualTo("UpdatedReceiver");
        assertThat(transactionArgumentCaptor.getValue().getSender()).isEqualTo("UpdatedSender");
    }

    @Test
    void updateTransaction_should_throwException_when_transactionNotFound() {
        String transactionId = "536dca89-81dc-46c8-91d4-6e59595caa7";
        TransactionNotFoundException transactionNotFoundException = assertThrows(TransactionNotFoundException.class, () -> transactionService.updateTransaction(transactionId, new TransactionUpdateRequest()));
        verify(mockTransactionRepository).findById(transactionId);
        assertThat(transactionNotFoundException.getMessage()).isEqualTo(ErrorMessage.TRANSACTION_NOT_FOUND.getMessage());
    }

    @Test
    void getTransactions_should_return_allTransactions() {

        Transaction transaction = new Transaction();
        transaction.setId("536dca89-81dc-46c8-91d4-6e59595caa7e");
        transaction.setValue(10000L);
        transaction.setSender("Dieudonne");
        transaction.setReceiver("Takougang");
        transaction.setConfirmed(Boolean.FALSE);
        transaction.setTimestamp(1634473449593L);

        when(mockTransactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));
        TransactionCollectionResponse allTransactions = transactionService.getTransactions();

        assertThat(allTransactions.getTransactionResource().get(0).getTransactionId()).isEqualTo("536dca89-81dc-46c8-91d4-6e59595caa7e");
        assertThat(allTransactions.getTransactionResource().get(0).getTransactionReceiver()).isEqualTo("Takougang");
        assertThat(allTransactions.getTransactionResource().get(0).getTransactionSender()).isEqualTo("Dieudonne");
        assertThat(allTransactions.getTransactionResource().get(0).getTransactionStatus()).isFalse();
        assertThat(allTransactions.getTransactionResource().get(0).getTransactionValue()).isEqualTo(10000L);
    }

    @Test
    void getTransaction_should_return_aTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId("dca89-81dc-46c8-91d4-6e59595caa7");
        transaction.setValue(20000L);
        transaction.setConfirmed(Boolean.TRUE);
        transaction.setSender("Dieudonne");
        transaction.setReceiver("Takougang");
        transaction.setTimestamp(1634473449593L);
        when(mockTransactionRepository.findById("dca89-81dc-46c8-91d4-6e59595caa7")).thenReturn(Optional.of(transaction));

        SimpleTransactionResponse simpleTransactionResponse = transactionService.getTransactionById("dca89-81dc-46c8-91d4-6e59595caa7");
        assertThat(simpleTransactionResponse.getTransactionValue()).isEqualTo(20000L);
        assertThat(simpleTransactionResponse.getTransactionId()).isEqualTo("dca89-81dc-46c8-91d4-6e59595caa7");
        assertThat(simpleTransactionResponse.getTransactionStatus()).isTrue();
        assertThat(simpleTransactionResponse.getTransactionSender()).isEqualTo("Dieudonne");
        assertThat(simpleTransactionResponse.getTransactionReceiver()).isEqualTo("Takougang");
    }

    @Test
    void getTransaction_should_throw_exception_whenTransactionNotFound() {
        TransactionNotFoundException transactionNotFoundException = assertThrows(TransactionNotFoundException.class, () -> transactionService.updateTransaction("transactionId", new TransactionUpdateRequest()));
        verify(mockTransactionRepository).findById("transactionId");
        assertThat(transactionNotFoundException.getMessage()).isEqualTo(ErrorMessage.TRANSACTION_NOT_FOUND.getMessage());
    }
}
