package br.ufsc.microsigner.crypto.service;

import br.ufsc.microsigner.crypto.exception.BadRequestException;
import br.ufsc.microsigner.crypto.exception.InternalServerErrorException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.security.KeyPair;
import java.security.Signature;


@Service
public class SignService {

  private final KeyService keyService;
  private final JWTVerifier verifier;
  private static final String HMAC256_KEY = "8c1c7821cbf79e552a78750f03ddfb4030ea4941bc117cef7e40b52126c8df5b20eded";
  private static final String BEARER_TOKEN_HEADER_START = "Bearer ";
  private static final String USER_ID_CLAIM = "userId";
  private static final String SIGN_ALGORITHM = "SHA256withRSA";


  public SignService(KeyService keyService) {
    this.keyService = keyService;
    var jwtSignAlgorithm = Algorithm.HMAC256(HMAC256_KEY);
    this.verifier = JWT.require(jwtSignAlgorithm).build();
  }


  public String sign(long userId, String text) {
    try {
      KeyPair keyPair = keyService.getKeyPairFromUser(userId);
      Signature signature = Signature.getInstance(SIGN_ALGORITHM);
      signature.initSign(keyPair.getPrivate());
      signature.update(text.getBytes());
      return Base64.getUrlEncoder().encodeToString(signature.sign());
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public long verifyJwtAndGetUserId(String authorizationHeader) {
    if (!authorizationHeader.startsWith(BEARER_TOKEN_HEADER_START)) {
      throw new BadRequestException("Invalid bearer token from authorization header.");
    }
    DecodedJWT decodedJwt = verifier.verify(authorizationHeader.substring(7));
    return decodedJwt.getClaim(USER_ID_CLAIM).asLong();
  }

}
