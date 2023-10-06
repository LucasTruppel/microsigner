package br.ufsc.microsigner.user.controller;

import br.ufsc.microsigner.user.dto.request.SignUpRequest;
import br.ufsc.microsigner.user.dto.response.SignUpResponse;
import br.ufsc.microsigner.user.entity.UserEntity;
import br.ufsc.microsigner.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  UserService userService;

  public Controller(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
    UserEntity user = userService.signUp(signUpRequest.getUsername(), signUpRequest.getName(),
            signUpRequest.getPassword());
    return new ResponseEntity<>(new SignUpResponse(user),HttpStatus.OK);
  }

}
