package br.ufsc.microsigner.user.controller;

import br.ufsc.microsigner.user.dto.request.LoginRequest;
import br.ufsc.microsigner.user.dto.request.SignUpRequest;
import br.ufsc.microsigner.user.dto.response.LoginResponse;
import br.ufsc.microsigner.user.dto.response.SignUpResponse;
import br.ufsc.microsigner.user.entity.UserEntity;
import br.ufsc.microsigner.user.service.UserService;
import jakarta.validation.Valid;
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

  @PostMapping("/signup")
  public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
    UserEntity user = userService.signUp(signUpRequest.getUsername(), signUpRequest.getName(),
            signUpRequest.getPassword());
    return new ResponseEntity<>(new SignUpResponse(user),HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    String jwt = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    return new ResponseEntity<>(new LoginResponse(jwt),HttpStatus.OK);
  }

}
