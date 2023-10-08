package br.ufsc.microsigner.crypto.exception;

import br.ufsc.microsigner.crypto.dto.response.ErrorResponse;
import br.ufsc.microsigner.crypto.dto.response.ValidationErrorResponse;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
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

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception e) {
    var errorResponse =  new ErrorResponse(e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(JWTVerificationException.class)
  public ResponseEntity<ErrorResponse> handleJWTVerificationException(Exception e) {
    var errorResponse =  new ErrorResponse("Invalid Json Web Token.");
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
    var errorResponse =  new ErrorResponse(e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
