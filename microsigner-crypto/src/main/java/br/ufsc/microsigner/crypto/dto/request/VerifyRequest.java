package br.ufsc.microsigner.crypto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class VerifyRequest {

  @NotBlank String text;
  @NotBlank String signatureBase64;
  @NotBlank String signerUsername;

}
