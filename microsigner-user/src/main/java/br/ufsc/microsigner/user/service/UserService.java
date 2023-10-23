package br.ufsc.microsigner.user.service;

import br.ufsc.microsigner.user.entity.UserEntity;
import br.ufsc.microsigner.user.exception.BadRequestException;
import br.ufsc.microsigner.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

@Service
public class UserService {

  UserRepository userRepository;
  private final MessageDigest sha256Digest;
  Algorithm jwtSignAlgorithm;

  private static final String HMAC256_KEY = "8c1c7821cbf79e552a78750f03ddfb4030ea4941bc117cef7e40b52126c8df5b20eded";
  private static final String USER_ID_CLAIM = "userId";

  public UserService(UserRepository userRepository) throws NoSuchAlgorithmException {
    this.userRepository = userRepository;
    this.sha256Digest = MessageDigest.getInstance("SHA-256");
    this.jwtSignAlgorithm = Algorithm.HMAC256(HMAC256_KEY);
  }

  public UserEntity signUp(String username, String name, String password) {
    if (userRepository.findFirstByUsername(username).isPresent()) {
      throw new BadRequestException("User already exists with given username.");
    }
    String passwordHashed = hashPassword(password);
    var user = new UserEntity(username, name, passwordHashed);
    return userRepository.save(user);
  }

  public String login(String username, String password) {
    String passwordHashed = hashPassword(password);
    UserEntity user =  userRepository.findFirstByUsername(username)
            .orElseThrow(() ->  new BadRequestException("User does not exist with given username."));
    if (!Objects.equals(user.getPasswordSha256(), passwordHashed)) {
      throw new BadRequestException("Wrong password.");
    }
    return JWT.create()
            .withClaim(USER_ID_CLAIM, user.getId())
            .withExpiresAt(Instant.now().plusSeconds(60 * 60))
            .sign(jwtSignAlgorithm);
  }

  private String hashPassword(String password) {
    byte[] passwordHashBytes = sha256Digest.digest(password.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(passwordHashBytes);
  }


  public long getIdByUsername(String username) {
    UserEntity user = userRepository.findFirstByUsername(username)
            .orElseThrow(() ->  new BadRequestException("User does not exist with given username."));
    return user.getId();
  }

}
