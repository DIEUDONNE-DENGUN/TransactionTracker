package org.iota.transactiontracking.api.domain.exception.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors = new HashMap<>();
}
