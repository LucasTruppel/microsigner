package br.ufsc.microsigner.user.dto.response;

import br.ufsc.microsigner.user.entity.UserEntity;
import lombok.Data;

@Data
public class SignUpResponse {

  String username;
  String name;

  public SignUpResponse(UserEntity userEntity) {
    username = userEntity.getUsername();
    name = userEntity.getName();
  }
}
