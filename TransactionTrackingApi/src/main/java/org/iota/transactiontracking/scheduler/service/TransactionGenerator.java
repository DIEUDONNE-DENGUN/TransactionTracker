package org.iota.transactiontracking.scheduler.service;


import org.iota.transactiontracking.scheduler.dto.TransactionDTO;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Generate random transaction request to be send to the api interface
 */
public interface TransactionGenerator {
    TransactionDTO generateRandomTransaction();
}
