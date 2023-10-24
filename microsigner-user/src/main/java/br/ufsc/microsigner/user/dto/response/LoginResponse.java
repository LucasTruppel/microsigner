package br.ufsc.microsigner.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginResponse {

  String jwt;
  String username;
  String name;

}
