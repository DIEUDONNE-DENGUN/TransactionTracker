package org.iota.transactiontracking.api.domain.exception.utils;

import lombok.Data;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class ErrorResponse {
    private String code;
    private String message;
    private String endpoint;
}
