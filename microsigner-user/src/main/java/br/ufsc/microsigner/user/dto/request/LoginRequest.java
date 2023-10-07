package br.ufsc.microsigner.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

  @NotBlank String username;
  @NotBlank String password;

}
