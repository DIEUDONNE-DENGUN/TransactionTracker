package org.iota.transactiontracking.api.domain.exception;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Handle a transaction not found exception
 */
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
