package br.ufsc.microsigner.crypto.repository;

import br.ufsc.microsigner.crypto.entity.KeyPairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyPairRepository extends JpaRepository<KeyPairEntity, Long> {

  Optional<KeyPairEntity> findFirstByUserId(long userId);


}
