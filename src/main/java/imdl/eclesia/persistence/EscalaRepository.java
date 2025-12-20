package imdl.eclesia.persistence;

import imdl.eclesia.entity.EscalaEntity;
import imdl.eclesia.entity.EscalaResumedEntity;
import imdl.eclesia.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EscalaRepository extends JpaRepository<EscalaEntity, UUID> {

    @Query("SELECT e FROM EscalaEntity e WHERE e.ministro.id = :levita " +
            "OR e.baixo.id = :levita " +
            "OR e.bateria.id = :levita " +
            "OR e.guitarra.id = :levita " +
            "OR e.teclado.id = :levita " +
            "OR e.violao.id = :levita " +
            "OR :levita IN (SELECT b.id FROM e.back b)")
    List<EscalaEntity> findAll(@Param("levita") UUID levita);

    @Query("SELECT e FROM EscalaEntity e WHERE MONTH(e.data) = :month")
    List<EscalaEntity> findAllInMonth(int month);

    @Query("SELECT e FROM EscalaEntity e WHERE e.data >= :atual AND e.data <= :limite")
    List<EscalaEntity> findNext(LocalDate atual, LocalDate limite);

    @Query("SELECT e FROM EscalaResumedEntity e WHERE e.data >= :atual AND e.data <= :limite")
    List<EscalaResumedEntity> findNextResumidas(LocalDate atual, LocalDate limite);

    @Query("SELECT e FROM EscalaResumedEntity e")
    List<EscalaResumedEntity> findAllResumida();

    @Query("SELECT e.musicas FROM EscalaEntity e WHERE e.id = :escalaId")
    List<MusicaEntity> findAllMusicasInEscala(UUID escalaId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EscalaEntity e WHERE " +
            "e.ministro.id = :levitaId OR " +
            "e.baixo.id = :levitaId OR " +
            "e.bateria.id = :levitaId OR " +
            "e.guitarra.id = :levitaId OR " +
            "e.teclado.id = :levitaId OR " +
            "e.violao.id = :levitaId OR " +
            ":levitaId IN (SELECT b.id FROM e.back b)")
    boolean existsByLevita(UUID levitaId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EscalaEntity e WHERE e.data IN :dates AND (" +
            "e.ministro.id = :levitaId OR " +
            "e.baixo.id = :levitaId OR " +
            "e.bateria.id = :levitaId OR " +
            "e.guitarra.id = :levitaId OR " +
            "e.teclado.id = :levitaId OR " +
            "e.violao.id = :levitaId OR " +
            ":levitaId IN (SELECT b.id FROM e.back b)" +
            ")")
    boolean existsByLevitaInDates(UUID levitaId, List<LocalDate> dates);
}
