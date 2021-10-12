package com.app.airport.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

  @ExceptionHandler(value = PersistenceException.class)
  void handlePersistenceException(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  void handleTransactionException(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  void handleIllegalArgumentsException(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(value = EntityNotFoundException.class)
  void handleEntityNotFoundException(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.NOT_FOUND.value());
  }
}
