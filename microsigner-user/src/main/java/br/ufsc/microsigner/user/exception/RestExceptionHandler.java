package br.ufsc.microsigner.user.exception;

import br.ufsc.microsigner.user.dto.response.ErrorResponse;
import br.ufsc.microsigner.user.exception.http.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestExcpetion(Exception e) {
    var errorResponse =  new ErrorResponse(e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
