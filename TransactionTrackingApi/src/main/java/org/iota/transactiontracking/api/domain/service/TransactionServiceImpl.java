package org.iota.transactiontracking.api.domain.service;

import lombok.RequiredArgsConstructor;
import org.iota.transactiontracking.api.domain.exception.TransactionNotFoundException;
import org.iota.transactiontracking.api.domain.exception.utils.ErrorMessage;
import org.iota.transactiontracking.api.domain.model.Transaction;
import org.iota.transactiontracking.api.domain.repository.TransactionRepository;
import org.iota.transactiontracking.api.domain.utils.TransactionMapper;
import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.iota.transactiontracking.api.response.SimpleTransactionResponse;
import org.iota.transactiontracking.api.response.TransactionCollectionResponse;
import org.iota.transactiontracking.api.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: service implementation for the transaction service interface
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public SimpleTransactionResponse saveTransaction(TransactionRequest transactionRequest) {
        //save record to db
        Transaction saveTransaction = transactionRepository.save(TransactionMapper.mapToTransactionEntity(transactionRequest));
        return TransactionMapper.mapToTransactionResponse(saveTransaction);
    }

    @Override
    public void updateTransaction(String transactionId, TransactionUpdateRequest transactionUpdateRequest) {
        Optional<Transaction> transactionExist = transactionRepository.findById(transactionId);
        if (!transactionExist.isPresent()) {
            throw new TransactionNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND.getMessage());
        }
        //update transaction record in db
        transactionRepository.save(TransactionMapper.mapToTransactionEntityUpdate(transactionUpdateRequest, transactionExist.get()));
    }

    @Override
    public SimpleTransactionResponse getTransactionById(String transactionId) {
        Optional<Transaction> transactionExist = transactionRepository.findById(transactionId);
        if (!transactionExist.isPresent()) {
            throw new TransactionNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND.getMessage());
        }
        return TransactionMapper.mapToTransactionResponse(transactionExist.get());
    }

    @Override
    public TransactionCollectionResponse getTransactions() {
        List<Transaction> transactionCollection = transactionRepository.findAll();
        return TransactionMapper.mapToTransactionCollectionResponse(transactionCollection);
    }

    @Override
    public TransactionCollectionResponse getTransactionsByDateRange(String startDate, String endDate) {
        return null;
    }

    @Override
    public TransactionCollectionResponse searchTransactionsByIdOrValue(String transactionId, Long value) {
        List<Transaction> transactionCollection = transactionRepository.findByIdOrValueOrderByCreatedAtDesc(transactionId, value);
        return TransactionMapper.mapToTransactionCollectionResponse(transactionCollection);
    }
}
