package br.ufsc.microsigner.user.service;

import br.ufsc.microsigner.user.entity.UserEntity;
import br.ufsc.microsigner.user.exception.http.BadRequestException;
import br.ufsc.microsigner.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserService {

  private final MessageDigest sha256Digest;
  UserRepository userRepository;

  public UserService(UserRepository userRepository) throws NoSuchAlgorithmException {
    this.userRepository = userRepository;
    this.sha256Digest = MessageDigest.getInstance("SHA-256");
  }

  public UserEntity signUp(String username, String name, String password) {
    if (userRepository.findFirstByUsername(username).isPresent()) {
      throw new BadRequestException("User already exists with given username.");
    }
    byte[] passwordHashBytes = sha256Digest.digest(password.getBytes(StandardCharsets.UTF_8));
    String passwordHashB64String = Base64.getEncoder().encodeToString(passwordHashBytes);
    var user = new UserEntity(username, name, passwordHashB64String);
    return userRepository.save(user);
  }

}
