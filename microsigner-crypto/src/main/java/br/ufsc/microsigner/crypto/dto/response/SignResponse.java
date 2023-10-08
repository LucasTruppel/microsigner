package br.ufsc.microsigner.crypto.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignResponse {

  String signatureBase64;
  String text;

}
