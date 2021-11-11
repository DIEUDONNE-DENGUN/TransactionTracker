package org.iota.transactiontracking.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.iota.transactiontracking.scheduler.config.TaskConfig;
import org.iota.transactiontracking.scheduler.dto.TransactionDTO;
import org.iota.transactiontracking.scheduler.dto.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: implementation client for the transaction task runner service
 */
@Service
@Slf4j
public class TransactionTaskServiceImpl implements TransactionTaskService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TransactionGenerator transactionGenerator;
    private RestTemplate restTemplate;
    private TaskConfig taskConfigProp;
    private String apiFullPath;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public TransactionTaskServiceImpl(TaskConfig taskConfigProp, @Qualifier("trackerRestTemplate") RestTemplate restTemplate, TransactionGenerator transactionGenerator, SimpMessagingTemplate simpMessagingTemplate) {
        this.transactionGenerator = transactionGenerator;
        this.restTemplate = restTemplate;
        this.taskConfigProp = taskConfigProp;
        this.apiFullPath = this.taskConfigProp.getApiHost() + this.taskConfigProp.getApiPath();
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void executeTask() {
        logger.info("Starting Task transaction scheduler");
        //generate a random transaction
        TransactionDTO transaction = transactionGenerator.generateRandomTransaction();
        logger.info("Generated a new random transaction request {}", transaction);
        //save to the generated transaction to the rest api
        logger.info("Making a restful api request for this transaction {}", transaction);
        TransactionResponse transactionResponse = saveTransactionRequest(transaction).getBody();
        //send websocket transaction updates
        publishTransactionUpdates(transactionResponse);
        logger.info("Transaction was saved on the rest api endpoint successfully  response: {}", transactionResponse);
        //instantiate the executor service to run a task of updating the transaction after 10s
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(new ScheduledTransactionScheduler(transactionResponse, restTemplate, apiFullPath, taskConfigProp.getApiKey()), taskConfigProp.getDuration(), TimeUnit.SECONDS);
        executorService.shutdown();
    }

    private ResponseEntity<TransactionResponse> saveTransactionRequest(TransactionDTO transactionDTO) {
        HttpHeaders httpHeaders = getRequestHeaderObject();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Authorization", "ApiKey " + taskConfigProp.getApiKey());
        HttpEntity<TransactionDTO> transactionRequest = new HttpEntity<>(transactionDTO, httpHeaders);
        return restTemplate.exchange(apiFullPath, HttpMethod.POST, transactionRequest, TransactionResponse.class);
    }

    private void publishTransactionUpdates(TransactionResponse transactionResponse) {
        logger.info("Sending transaction to websocket: {}", transactionResponse);
        simpMessagingTemplate.convertAndSend("/topic/transactions", transactionResponse);
    }

    private HttpHeaders getRequestHeaderObject() {
        return new HttpHeaders();
    }

}