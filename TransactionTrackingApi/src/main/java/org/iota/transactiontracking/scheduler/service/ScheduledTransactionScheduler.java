package org.iota.transactiontracking.scheduler.service;

import org.iota.transactiontracking.scheduler.dto.TransactionResponse;
import org.iota.transactiontracking.scheduler.dto.TransactionUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.Callable;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
public class ScheduledTransactionScheduler implements Callable<TransactionResponse> {
    private final TransactionResponse transactionResponse;
    private final RestTemplate restTemplate;
    private final String urlPath;
    private final String apiKey;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ScheduledTransactionScheduler(TransactionResponse transactionResponse, RestTemplate restTemplate, String url, String apiKey) {
        this.transactionResponse = transactionResponse;
        this.restTemplate = restTemplate;
        this.urlPath = url;
        this.apiKey = apiKey;
    }

    @Override
    public TransactionResponse call() throws Exception {
        updateTransactionConfirmedStatus(transactionResponse);
        return transactionResponse;
    }

    private void updateTransactionConfirmedStatus(TransactionResponse transactionResponse) {
        logger.info("About updating  a transaction record after 10s  {}", transactionResponse);
        TransactionUpdateDTO transactionUpdateDTO = new TransactionUpdateDTO();
        transactionUpdateDTO.setConfirmed(Boolean.TRUE);
        transactionUpdateDTO.setReceiver(transactionResponse.getTransactionReceiver());
        transactionUpdateDTO.setSender(transactionResponse.getTransactionSender());
        transactionUpdateDTO.setValue(transactionResponse.getTransactionValue());
        logger.info("Making a restful api request to update transaction confirmed status {}", transactionUpdateDTO);
        //update the recently created transaction after 10s
        updateTransactionRequestApi(transactionUpdateDTO, transactionResponse.getTransactionId());
        logger.info("Transaction was updated successfully");
    }

    private ResponseEntity<Void> updateTransactionRequestApi(TransactionUpdateDTO transactionDTO, String transactionId) {

        String apiUrl = urlPath + "/" + transactionId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Authorization", "ApiKey " + apiKey);
        HttpEntity<TransactionUpdateDTO> transactionRequest = new HttpEntity<>(transactionDTO, httpHeaders);
        return restTemplate.exchange(apiUrl, HttpMethod.PUT, transactionRequest, Void.class);
    }
}
