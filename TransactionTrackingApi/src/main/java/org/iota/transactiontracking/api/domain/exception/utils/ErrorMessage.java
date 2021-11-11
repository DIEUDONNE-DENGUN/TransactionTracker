package org.iota.transactiontracking.api.domain.exception.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: keep track of possible error messages on the app
 */
public enum ErrorMessage {
    ACCESS_DENIED("Unauthorized access or invalid or expired Api Access Key Information"),
    VALIDATION_ERROR("Validation errors. Kindly enter all required fields"),
    TRANSACTION_NOT_FOUND("Transaction Not found for the selected transaction identifier"),
    TRANSACTION_ALREADY_EXIST("Transaction Identifier already in used");
    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
