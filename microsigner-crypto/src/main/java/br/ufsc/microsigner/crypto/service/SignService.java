package br.ufsc.microsigner.crypto.service;

import br.ufsc.microsigner.crypto.entity.UserEntity;
import br.ufsc.microsigner.crypto.exception.BadRequestException;
import br.ufsc.microsigner.crypto.exception.InternalServerErrorException;
import br.ufsc.microsigner.crypto.repository.UserRepository;
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
  private final UserRepository userRepository;
  private final JWTVerifier jwtVerifier;
  private static final String HMAC256_KEY = "8c1c7821cbf79e552a78750f03ddfb4030ea4941bc117cef7e40b52126c8df5b20eded";
  private static final String BEARER_TOKEN_HEADER_START = "Bearer ";
  private static final String USER_ID_CLAIM = "userId";
  private static final String SIGN_ALGORITHM = "SHA256withRSA";


  public SignService(KeyService keyService, UserRepository userRepository) {
    this.keyService = keyService;
    this.userRepository = userRepository;
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
    } catch (Exception e) {
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
    UserEntity user =  userRepository.findFirstByUsername(signerUsername)
            .orElseThrow(() ->  new BadRequestException("User does not exist with given username."));
    try {
      KeyPair keyPair = keyService.getKeyPairFromUser(user.getId());
      Signature sig = Signature.getInstance(SIGN_ALGORITHM);
      sig.initVerify(keyPair.getPublic());
      sig.update(text.getBytes());
      return sig.verify(Base64.getUrlDecoder().decode(signatureBase64));
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

}
