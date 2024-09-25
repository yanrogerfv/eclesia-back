package imdl.scalator.auth.repository;

import imdl.scalator.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
}
