package br.ufsc.microsigner.crypto.service;

import br.ufsc.microsigner.crypto.exception.BadRequestException;
import br.ufsc.microsigner.crypto.exception.InternalServerErrorException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.*;
import java.util.Base64;


@Service
public class SignService {

  private final KeyService keyService;
  private final JWTVerifier jwtVerifier;
  @Value("${microsigner-user-url}")
  private String microsignerUserUrl;
  private static final String HMAC256_KEY = "8c1c7821cbf79e552a78750f03ddfb4030ea4941bc117cef7e40b52126c8df5b20eded";
  private static final String BEARER_TOKEN_HEADER_START = "Bearer ";
  private static final String USER_ID_CLAIM = "userId";
  private static final String SIGN_ALGORITHM = "SHA256withRSA";



  public SignService(KeyService keyService) {
    this.keyService = keyService;
    var jwtSignAlgorithm = Algorithm.HMAC256(HMAC256_KEY);
    this.jwtVerifier = JWT.require(jwtSignAlgorithm).build();
  }


  public String sign(long userId, String text) {
    try {
      KeyPair keyPair = keyService.getKeyPairFromUser(userId);
      Signature signature = Signature.getInstance(SIGN_ALGORITHM);
      signature.initSign(keyPair.getPrivate());
      signature.update(text.getBytes());
      return Base64.getUrlEncoder().encodeToString(signature.sign());
    } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public long verifyJwtAndGetUserId(String authorizationHeader) {
    if (!authorizationHeader.startsWith(BEARER_TOKEN_HEADER_START)) {
      throw new BadRequestException("Invalid bearer token from authorization header.");
    }
    DecodedJWT decodedJwt = jwtVerifier.verify(authorizationHeader.substring(7));
    return decodedJwt.getClaim(USER_ID_CLAIM).asLong();
  }

  public boolean verifySignature(String text, String signatureBase64, String signerUsername) {
    long userId = findUserIdByUsername(signerUsername);
    try {
      KeyPair keyPair = keyService.getKeyPairFromUser(userId);
      Signature sig = Signature.getInstance(SIGN_ALGORITHM);
      sig.initVerify(keyPair.getPublic());
      sig.update(text.getBytes());
      return sig.verify(Base64.getUrlDecoder().decode(signatureBase64));
    } catch (IllegalArgumentException | SignatureException e) {
      return false;
    }
    catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new InternalServerErrorException(e);
    }
  }

  private long findUserIdByUsername(String signerUsername) {
    try {
      URI uri = URI.create(microsignerUserUrl + "/user/" + signerUsername);
      HttpClient httpClient = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest
              .newBuilder()
              .uri(uri)
              .header("accept", "application/json")
              .GET()
              .build();
      var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      JSONObject json = new JSONObject(response.body());
      if (response.statusCode() == 200) {
        return json.getLong("userId");
      } else if (response.statusCode() == 400) {
        throw new BadRequestException(json.getString("message"));
      } else {
        throw new RuntimeException(json.getString("message"));
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
