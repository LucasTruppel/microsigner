package br.ufsc.microsigner.crypto.exception;

public class BadRequestException extends RuntimeException{

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Exception e) {
    super(message, e);
  }
}
