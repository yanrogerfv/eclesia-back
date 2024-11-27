package imdl.scalator.persistence;

import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.entity.EscalaResumidaEntity;
import imdl.scalator.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EscalaRepository extends JpaRepository<EscalaEntity, UUID> {

    @Query("SELECT e FROM EscalaEntity e WHERE MONTH(e.data) = :month")
    List<EscalaEntity> findAllInMonth(int month);

    @Query("SELECT e FROM EscalaEntity e WHERE e.data >= :atual AND e.data <= :limite")
    List<EscalaEntity> findNext(LocalDate atual, LocalDate limite);

    @Query("SELECT e FROM EscalaResumidaEntity e WHERE e.data >= :atual AND e.data <= :limite")
    List<EscalaResumidaEntity> findNextResumidas(LocalDate atual, LocalDate limite);

    @Query("SELECT e FROM EscalaResumidaEntity e")
    List<EscalaResumidaEntity> findAllResumida();

    @Query("SELECT e.musicas FROM EscalaEntity e WHERE e.id = :escalaId")
    List<MusicaEntity> findAllMusicasInEscala(UUID escalaId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EscalaEntity e WHERE e.ministro.id = :levitaId")
    boolean existsByLevita(UUID levitaId);
}
