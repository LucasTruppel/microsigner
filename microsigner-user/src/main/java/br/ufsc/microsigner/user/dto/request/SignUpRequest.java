package br.ufsc.microsigner.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

  @NotBlank String username;
  @NotBlank String name;
  @NotBlank String password;

}
