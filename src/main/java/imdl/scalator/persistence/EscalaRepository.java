package imdl.scalator.persistence;

import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EscalaRepository extends JpaRepository<EscalaEntity, UUID> {

    @Query("SELECT e FROM EscalaEntity e WHERE MONTH(e.data) = :month")
    List<EscalaEntity> findAllInMonth(int month);

    @Query("SELECT e.musicas FROM EscalaEntity e WHERE e.id = :escalaId")
    List<MusicaEntity> findAllMusicasInEscala(UUID escalaId);

}
