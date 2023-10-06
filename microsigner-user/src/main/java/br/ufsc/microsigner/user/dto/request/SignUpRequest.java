package br.ufsc.microsigner.user.dto.request;

import lombok.Data;

@Data
public class SignUpRequest {

  String username;
  String name;
  String password;

}
