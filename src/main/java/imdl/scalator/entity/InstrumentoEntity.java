package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instrumentos")
public class InstrumentoEntity {
    @Id
    @Column(name = "instrumento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
