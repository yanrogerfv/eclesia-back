package imdl.scalator.persistence;

import imdl.scalator.entity.EscalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EscalaRepository extends JpaRepository<EscalaEntity, UUID> {
}
