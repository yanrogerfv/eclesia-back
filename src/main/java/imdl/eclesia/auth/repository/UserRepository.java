package imdl.eclesia.auth.repository;

import imdl.eclesia.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.username = :username AND u.accessCode <> :accessCode")
    boolean existsByUsernameWithDifferentCode(String username, String accessCode);
    boolean existsByLevitaId(UUID levitaId);
    boolean existsByAccessCode(String accessCode);

    List<UserEntity> findAllByActiveFalse();

    Optional<UserEntity> findByAccessCode(String accessCode);
}
