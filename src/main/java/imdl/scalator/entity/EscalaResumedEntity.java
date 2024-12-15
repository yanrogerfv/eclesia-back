package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "escala")
public class EscalaResumedEntity {
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
    private String ministroNome;
    private String baixoNome;
    private String bateriaNome;
    private String guitarraNome;
    private String tecladoNome;
    private String violaoNome;
    private String observacoes;
}
