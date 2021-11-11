package org.iota.transactiontracking.api.domain.repository;

import org.iota.transactiontracking.api.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: handle data layer by persisting entities to database
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    //find all transactions which match a given search transaction Id or value
    List<Transaction> findByIdOrValueOrderByCreatedAtDesc(String transactionId, Long value);
}
