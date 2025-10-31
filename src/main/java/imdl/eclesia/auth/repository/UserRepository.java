package imdl.eclesia.auth.repository;

import aj.org.objectweb.asm.commons.Remapper;
import imdl.eclesia.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByLevitaId(UUID levitaId);
    boolean existsByAccessCode(String accessCode);

    List<UserEntity> findAllByActiveFalse();

    Optional<UserEntity> findByAccessCode(String accessCode);
}
