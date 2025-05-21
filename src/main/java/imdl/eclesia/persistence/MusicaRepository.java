package imdl.eclesia.persistence;

import imdl.eclesia.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicaRepository extends JpaRepository<MusicaEntity, UUID> {

}
