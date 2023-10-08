package br.ufsc.microsigner.crypto.dto.response;

import lombok.Data;
import org.springframework.validation.FieldError;


@Data
public class ValidationErrorResponse {

  String field;
  String message;

  public ValidationErrorResponse(FieldError error) {
    this.field = error.getField();
    this.message = error.getDefaultMessage();
  }

}
