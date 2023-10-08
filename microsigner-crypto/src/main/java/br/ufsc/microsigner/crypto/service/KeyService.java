package br.ufsc.microsigner.crypto.service;

import br.ufsc.microsigner.crypto.entity.KeyPairEntity;
import br.ufsc.microsigner.crypto.exception.InternalServerErrorException;
import br.ufsc.microsigner.crypto.repository.KeyPairRepository;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
public class KeyService {
  private final KeyPairRepository keyPairRepository;
  KeyPairGenerator keyPairGenerator;
  private static final String KEY_ALGORITHM = "RSA";
  private static final int keySize = 2048;

  public KeyService(KeyPairRepository keyPairRepository) throws NoSuchAlgorithmException {
    this.keyPairRepository = keyPairRepository;
    keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
    keyPairGenerator.initialize(keySize);
  }

  public KeyPair getKeyPairFromUser(long userId) {
    try {
      Optional<KeyPairEntity> keyPairEntityOptional = keyPairRepository.findFirstByUserId(userId);
      if (keyPairEntityOptional.isEmpty()) {
        return generateKeyPairToUser(userId);
      }
      KeyPairEntity keyPairEntity = keyPairEntityOptional.get();
      PublicKey publicKey = getPublicKeyFromBytes(keyPairEntity.getPublicKey());
      PrivateKey privateKey = getPrivateKeyFromBytes(keyPairEntity.getPrivateKey());
      return new KeyPair(publicKey, privateKey);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  private KeyPair generateKeyPairToUser(long userId) {
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    KeyPairEntity keyPairEntity = new KeyPairEntity(
            Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()),
            Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()),
            userId);
    keyPairRepository.save(keyPairEntity);
    return keyPair;
  }


  private PublicKey getPublicKeyFromBytes(String publicKeyB64) throws InvalidKeySpecException,
          NoSuchAlgorithmException {
    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyB64);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
    return keyFactory.generatePublic(publicKeySpec);
  }

  private PrivateKey getPrivateKeyFromBytes(String privateKeyB64) throws InvalidKeySpecException,
          NoSuchAlgorithmException {
    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyB64);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    return keyFactory.generatePrivate(privateKeySpec);
  }

}
