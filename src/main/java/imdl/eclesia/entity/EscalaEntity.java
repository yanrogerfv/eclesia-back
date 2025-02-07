package imdl.eclesia.entity;

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
    private UUID id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "titulo")
    private String titulo;

    private boolean quarta;
    private boolean domingo;
    private boolean especial;

    @ManyToOne
    @JoinColumn(name = "ministro_id", referencedColumnName = "levita_id")
    private LevitaEntity ministro;
    private String ministroNome;
    @ManyToOne
    @JoinColumn(name = "baixo_id", referencedColumnName = "levita_id")
    private LevitaEntity baixo;
    private String baixoNome;
    @ManyToOne
    @JoinColumn(name = "bateria_id", referencedColumnName = "levita_id")
    private LevitaEntity bateria;
    private String bateriaNome;
    @ManyToOne
    @JoinColumn(name = "guitarra_id", referencedColumnName = "levita_id")
    private LevitaEntity guitarra;
    private String guitarraNome;
    @ManyToOne
    @JoinColumn(name = "teclado_id", referencedColumnName = "levita_id")
    private LevitaEntity teclado;
    private String tecladoNome;
    @ManyToOne
    @JoinColumn(name = "violao_id", referencedColumnName = "levita_id")
    private LevitaEntity violao;
    private String violaoNome;
    @ManyToMany
    @JoinTable(
            name = "backs_in_escala",
            joinColumns = @JoinColumn(name = "escala_id"),
            inverseJoinColumns = @JoinColumn(name = "levita_id")
    )
    private List<LevitaEntity> back = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "musicas_in_escala",
            joinColumns = @JoinColumn(name = "escala_id", referencedColumnName = "escala_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id", referencedColumnName = "musica_id")
    )
    private List<MusicaEntity> musicas;
    private String observacoes;

}
