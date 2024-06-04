package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "levita")
public class LevitaEntity {
    @Id
    @Column(name = "levita_id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private int instrumento;
    private String contato;
    private String email;
    private boolean disponivel;

    @OneToMany
    private List<EscalaEntity> escalas = new ArrayList<>();
}
