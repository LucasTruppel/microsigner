package br.ufsc.microsigner.user.repository;

import br.ufsc.microsigner.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findFirstByUsername(String username);

}
