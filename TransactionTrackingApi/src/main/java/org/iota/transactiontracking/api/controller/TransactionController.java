package org.iota.transactiontracking.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.iota.transactiontracking.api.response.SimpleTransactionResponse;
import org.iota.transactiontracking.api.response.TransactionCollectionResponse;
import org.iota.transactiontracking.api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/protected")
@Api(value = "Transaction Tracking System", description = "A restful api transaction module for the tracking system")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    @ApiOperation(value = "create a new transaction record", response = SimpleTransactionResponse.class)
    public ResponseEntity<SimpleTransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        SimpleTransactionResponse saveTransaction = transactionService.saveTransaction(transactionRequest);
        return new ResponseEntity<>(saveTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/transactions/{transactionId}")
    @ApiOperation(value = "update an existing transaction by its Identifier", response = Void.class)
    public ResponseEntity<Void> updateTransaction(@PathVariable("transactionId") String transactionId, @Valid @RequestBody TransactionUpdateRequest transactionUpdateRequest) {
        transactionService.updateTransaction(transactionId, transactionUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transactions/{transactionId}")
    @ApiOperation(value = "get a transaction details by its identifier", response = SimpleTransactionResponse.class)
    public ResponseEntity<SimpleTransactionResponse> getTransactionById(@PathVariable("transactionId") String transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }

    @GetMapping("/transactions")
    @ApiOperation(value = "View a list of existing transactions or view by start and end date range for better sorting", response = TransactionCollectionResponse.class)
    public ResponseEntity<TransactionCollectionResponse> getTransactions(@RequestParam(required = false, defaultValue = "") String startDate, @RequestParam(required = false, defaultValue = "") String endDate) {
        if (startDate.equalsIgnoreCase("") && endDate.equalsIgnoreCase("")) {
            return ResponseEntity.ok(transactionService.getTransactions());
        }
        return ResponseEntity.ok(transactionService.getTransactionsByDateRange(startDate, endDate));
    }

    @GetMapping("/transactions/searches")
    @ApiOperation(value = "Search transactions which match a given transaction id or value", response = TransactionCollectionResponse.class)
    public ResponseEntity<TransactionCollectionResponse> searchTransactionByIdOrValue(@RequestParam(required = false, defaultValue = "") String transactionId, @RequestParam(required = false) Long value) {
        return ResponseEntity.ok(transactionService.searchTransactionsByIdOrValue(transactionId, value));
    }
}
