package imdl.scalator.persistence;

import imdl.scalator.entity.InstrumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstrumentoRepository extends JpaRepository<InstrumentoEntity, Long> {

}
