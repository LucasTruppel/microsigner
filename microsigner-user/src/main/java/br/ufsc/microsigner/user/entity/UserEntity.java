package br.ufsc.microsigner.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="username", columnDefinition = "TEXT")
  private String username;

  @Column(name="name", columnDefinition = "TEXT")
  private String name;

  @Column(name="password_sha256", columnDefinition = "TEXT")
  private String passwordSha256;

  public UserEntity(String username, String name, String passwordSha256) {
    this.username = username;
    this.name = name;
    this.passwordSha256 = passwordSha256;
  }

}
