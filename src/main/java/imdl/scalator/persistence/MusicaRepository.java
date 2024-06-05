package imdl.scalator.persistence;

import imdl.scalator.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MusicaRepository extends JpaRepository<MusicaEntity, UUID> {

}
