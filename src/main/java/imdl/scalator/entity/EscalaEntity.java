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
@Table(name = "escala")
public class EscalaEntity {
    @Id
    @Column(name = "escala_id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID escalaId;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "titulo")
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "ministro_id", referencedColumnName = "levita_id")
    private LevitaEntity ministro;
    @ManyToOne
    @JoinColumn(name = "violao_id", referencedColumnName = "levita_id")
    private LevitaEntity violao;
    @ManyToOne
    @JoinColumn(name = "teclado_id", referencedColumnName = "levita_id")
    private LevitaEntity teclado;
    @ManyToOne
    @JoinColumn(name = "baixo_id", referencedColumnName = "levita_id")
    private LevitaEntity baixo;
    @ManyToOne
    @JoinColumn(name = "bateria_id", referencedColumnName = "levita_id")
    private LevitaEntity bateria;
    /*@ManyToMany
    @JoinTable(
            name = "escala_levita",
            joinColumns = @JoinColumn(name = "escala_id"),
            inverseJoinColumns = @JoinColumn(name = "levita_id")
    )
    private List<LevitaEntity> back = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "escala_musica",
            joinColumns = @JoinColumn(name = "escala_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private List<MusicaEntity> musicas;*/
    private String observacoes;

}
