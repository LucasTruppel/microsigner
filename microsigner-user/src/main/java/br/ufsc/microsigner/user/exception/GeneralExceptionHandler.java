package br.ufsc.microsigner.user.exception;

import br.ufsc.microsigner.user.dto.response.ErrorResponse;
import br.ufsc.microsigner.user.dto.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GeneralExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestExcpetion(Exception e) {
    var errorResponse =  new ErrorResponse(e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ValidationErrorResponse>> tratarErro400(MethodArgumentNotValidException ex) {
    var errors = ex.getFieldErrors();
    var errorResponse = errors.stream().map(ValidationErrorResponse::new).toList();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
