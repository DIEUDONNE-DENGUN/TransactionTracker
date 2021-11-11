package org.iota.transactiontracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactiontrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactiontrackingApplication.class, args);
	}

}
