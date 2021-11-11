package org.iota.transactiontracking.scheduler.service;

import com.github.javafaker.Faker;
import org.iota.transactiontracking.scheduler.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Service
public class TransactionGeneratorImpl implements TransactionGenerator {

    @Override
    public TransactionDTO generateRandomTransaction() {
        Faker fakerService = new Faker();
        int value = fakerService.number().randomDigitNotZero() *10000;
        String sender = fakerService.name().firstName();
        String receiver = fakerService.name().lastName();
        //generate timestamp
        Timestamp timestampObject = new Timestamp(System.currentTimeMillis());
        Long timestamp = timestampObject.getTime();
        //create a new transaction object on the generated details
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setConfirmed(false);
        transactionDTO.setReceiver(receiver);
        transactionDTO.setSender(sender);
        transactionDTO.setValue(Long.valueOf(value));
        transactionDTO.setTimestamp(timestamp);
        return transactionDTO;
    }
}
