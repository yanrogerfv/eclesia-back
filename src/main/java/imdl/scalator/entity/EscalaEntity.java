package imdl.scalator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
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
    private UUID backs;
    private UUID musicas;
    private LocalDate date;

}
