package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class LevitaEntity {
    @Id
    @Column(name = "levita_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;
    private String nome;
    private int instrumento;
    private String contato;
    private String email;
}
