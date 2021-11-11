package org.iota.transactiontracking.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
 */
@Data
public class TransactionUpdateRequest {
    @NotNull
    private Long value;
    @NotNull
    private String receiver;
    @NotNull
    private String sender;
    @NotNull
    private boolean confirmed;
}
