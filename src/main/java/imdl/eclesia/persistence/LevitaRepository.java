package imdl.eclesia.persistence;

import imdl.eclesia.entity.LevitaEntity;
import imdl.eclesia.entity.LevitaResumedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LevitaRepository extends JpaRepository<LevitaEntity, UUID> {

    @Query("SELECT l FROM LevitaEntity l LEFT JOIN l.instrumentos i WHERE i.id = :instrumento")
    List<LevitaEntity> findAllByInstrumento(Long instrumento);

    @Query("SELECT l FROM LevitaEntity l " +
            "LEFT JOIN l.instrumentos i WHERE (:instrumento IS NULL OR i.id = :instrumento)" +
            "AND (:nome IS NULL OR LOWER(l.nome) LIKE CONCAT('%',LOWER(CAST(:nome AS text)),'%')  ) ")
    List<LevitaEntity> findAll(@Param("nome") String nome,
                               @Param("instrumento") Long instrumento);

    @Query("SELECT l from LevitaResumedEntity l")
    List<LevitaResumedEntity> findAllResumed();
}
