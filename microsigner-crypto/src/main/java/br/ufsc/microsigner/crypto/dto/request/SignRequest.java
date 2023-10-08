package br.ufsc.microsigner.crypto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SignRequest {

  @NotBlank String text;

}
