package imdl.scalator.persistence;

import imdl.scalator.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MusicasRepository extends JpaRepository<MusicaEntity, UUID> {

    @Query("SELECT m FROM MusicaEntity m WHERE m.escala = :id_escala")
    List<MusicaEntity> findAllInEscala(@Param("id_escala")UUID escalaId);
}
