package imdl.scalator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class EscalaEntity {
    private UUID escalaId;
    private UUID ministro;
    private UUID violao;
    private UUID teclado;
    private UUID baixo;
    private UUID bateria;
    @OneToMany
    private List<UUID> back;
    @OneToMany
    private List<UUID> musicas;
    private LocalDate date;

}
