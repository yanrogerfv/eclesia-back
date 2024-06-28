package imdl.scalator.persistence;

import imdl.scalator.entity.LevitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LevitaRepository extends JpaRepository<LevitaEntity, UUID> {

    @Query("SELECT l FROM LevitaEntity l LEFT JOIN l.instrumentos i WHERE i.id = :instrumento")
    List<LevitaEntity> findAllByInstrumento(Long instrumento);
}
