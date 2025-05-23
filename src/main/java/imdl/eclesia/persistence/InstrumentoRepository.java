package imdl.eclesia.persistence;

import imdl.eclesia.entity.InstrumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstrumentoRepository extends JpaRepository<InstrumentoEntity, Long> {

    boolean existsByNome(String nome);

    @Query("SELECT (MAX(i.id)+1) FROM InstrumentoEntity i")
    Long findNextSequential();

}
