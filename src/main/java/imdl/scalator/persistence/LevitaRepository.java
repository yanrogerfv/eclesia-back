package imdl.scalator.persistence;

import imdl.scalator.entity.LevitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LevitaRepository extends JpaRepository<LevitaEntity, UUID> {

    @Query("SELECT l FROM LevitaEntity l WHERE l.instrumento = :inst")
    List<LevitaEntity> findAllByInstrumento(int inst);
}
