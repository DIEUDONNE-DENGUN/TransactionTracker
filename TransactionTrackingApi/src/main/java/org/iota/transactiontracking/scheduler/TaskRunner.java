package org.iota.transactiontracking.scheduler;

import org.iota.transactiontracking.scheduler.service.TransactionTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Component
@EnableAsync
public class TaskRunner {

    private final TransactionTaskService transactionTaskService;

    @Autowired
    public TaskRunner(TransactionTaskService transactionTaskService) {
        this.transactionTaskService = transactionTaskService;
    }

    @Async
    @Scheduled(fixedRate = 60000)
    public void runTransactionScheduler() {
        transactionTaskService.executeTask();
    }
}
