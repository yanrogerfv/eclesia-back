package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instrumentos")
public class InstrumentoEntity {
    @Id
    @Column(name = "numero")
    private Long id;
    private String nome;
}
