package br.ufsc.microsigner.crypto.controller;

import br.ufsc.microsigner.crypto.dto.request.SignRequest;
import br.ufsc.microsigner.crypto.dto.response.SignResponse;
import br.ufsc.microsigner.crypto.service.SignService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {

  SignService signService;

  public CryptoController(SignService signService) {
    this.signService = signService;
  }

  @PostMapping("/sign")
  public ResponseEntity<SignResponse> signUp(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                             @RequestBody @Valid SignRequest signRequest) {
    long userId = signService.verifyJwtAndGetUserId(authorizationHeader);
    String signatureB64 = signService.sign(userId, signRequest.getText());
    return new ResponseEntity<>(new SignResponse(signatureB64, signRequest.getText()), HttpStatus.OK);
  }

}
