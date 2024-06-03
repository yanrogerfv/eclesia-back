package imdl.scalator.entity;

import imdl.scalator.domain.Musica;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class EscalaEntity {
    @Id
    @Column(name = "escala_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID escalaId;
    private String titulo;

    private LevitaEntity ministro;
    private LevitaEntity violao;
    private LevitaEntity teclado;
    private LevitaEntity baixo;
    private LevitaEntity bateria;
    @ManyToMany(mappedBy = "escalas")
    @JoinTable(
            name = "escala_levita",
            joinColumns = @JoinColumn(name = "escala_id"),
            inverseJoinColumns = @JoinColumn(name = "levita_id")
    )
    private List<LevitaEntity> back = new ArrayList<>();
    @ManyToMany(mappedBy = "escalas")
    @JoinTable(
            name = "escala_musica",
            joinColumns = @JoinColumn(name = "escala_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private List<MusicaEntity> musicas;
    private LocalDate data;
    private String observacoes;

}
