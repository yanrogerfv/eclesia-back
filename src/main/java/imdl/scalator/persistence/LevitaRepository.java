package imdl.scalator.persistence;

import imdl.scalator.entity.LevitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LevitaRepository extends JpaRepository<LevitaEntity, UUID> {
}
