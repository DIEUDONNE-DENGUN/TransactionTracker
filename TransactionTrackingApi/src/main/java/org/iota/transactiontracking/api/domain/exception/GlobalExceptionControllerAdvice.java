package org.iota.transactiontracking.api.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.iota.transactiontracking.api.domain.exception.utils.ErrorMessage;
import org.iota.transactiontracking.api.domain.exception.utils.ErrorResponse;
import org.iota.transactiontracking.api.domain.exception.utils.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: Handle the entire application exceptions and return the appropriate response
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(value = {TransactionNotFoundException.class}) //404
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException transactionNotFoundException, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ErrorMessage.TRANSACTION_NOT_FOUND.toString());
        errorResponse.setMessage(transactionNotFoundException.getMessage());
        errorResponse.setEndpoint(httpServletRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AccessDeniedException.class}) //401
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ErrorMessage.ACCESS_DENIED.toString());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setEndpoint(request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class}) //422
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                         HttpServletRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setEndpoint(request.getRequestURI());
        validationErrorResponse.setCode(ErrorMessage.VALIDATION_ERROR.toString());
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        StringBuilder message = new StringBuilder("MethodArgumentNotValidException:");

        for (FieldError fieldError : fieldErrorList) {
            validationErrorResponse.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
            message.append(" #").append(fieldError.getField());
        }
        message.append(" @errors.");
        validationErrorResponse.setMessage(message.toString());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
