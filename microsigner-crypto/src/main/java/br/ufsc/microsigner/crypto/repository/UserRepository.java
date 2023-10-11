package br.ufsc.microsigner.crypto.repository;

import br.ufsc.microsigner.crypto.entity.UserEntity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findFirstByUsername(String username);

}
