package imdl.eclesia.persistence;

import imdl.eclesia.entity.EscalaLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EscalaLogRepository extends JpaRepository<EscalaLogEntity, UUID> {
}
