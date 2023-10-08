package br.ufsc.microsigner.crypto.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="key_pair")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class KeyPairEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="public_key", columnDefinition = "TEXT")
  private String publicKey;

  @Column(name="private_key", columnDefinition = "TEXT")
  private String privateKey;

  @Column(name="user_id", columnDefinition = "BIGINT")
  private long userId;

  public KeyPairEntity(String publicKey, String privateKey, long userId) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
    this.userId = userId;
  }

}