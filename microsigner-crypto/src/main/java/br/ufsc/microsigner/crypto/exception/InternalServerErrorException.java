package br.ufsc.microsigner.crypto.exception;


public class InternalServerErrorException extends RuntimeException{

  public InternalServerErrorException() {
    super("Unexpected Internal Server Error.");
  }

  public InternalServerErrorException(Exception e) {
    super("Unexpected Internal Server Error.", e);
  }
}
