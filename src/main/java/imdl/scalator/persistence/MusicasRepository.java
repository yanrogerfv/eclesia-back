package imdl.scalator.persistence;

import imdl.scalator.domain.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicasRepository extends JpaRepository<Musica, UUID> {
}
