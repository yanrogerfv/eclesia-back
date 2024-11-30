package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "levita")
public class LevitaResumedEntity {
    @Id
    @Column(name = "levita_id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String contato;
    private String email;
    private String descricao;
    @ManyToMany
    @JoinTable(
            name = "levita_instrumentos",
            joinColumns = @JoinColumn(name = "levita_id", referencedColumnName = "levita_id"),
            inverseJoinColumns = @JoinColumn(name = "instrumento", referencedColumnName = "numero")
    ) private List<InstrumentoEntity> instrumentos;
    private List<LocalDate> agenda;
}
