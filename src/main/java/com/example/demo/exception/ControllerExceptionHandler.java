package com.example.demo.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<String> handleException(Exception e) {
  // log.error("Exception caught in handleRuntimeException : {} ", e);
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  // }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ErrorResponse> handleDuplicatedUsernameException(Exception e) {
    log.error("Duplicated user during signup");
    return new ResponseEntity<>(new ErrorResponse("User already exists"), HttpStatus.BAD_REQUEST);
  }
}
