package org.iota.transactiontracking.api.domain.exception;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Handle a invalid api key exception
 */
public class AccessDeniedException  extends RuntimeException{
    public AccessDeniedException(String message){super(message);}
}
