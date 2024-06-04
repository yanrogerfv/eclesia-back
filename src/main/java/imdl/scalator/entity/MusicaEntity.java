package imdl.scalator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "musica")
public class MusicaEntity {
    @Id
    @Column(name = "musica_id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String link;
    @Column(name = "escala", columnDefinition = "uuid")
    private UUID escala;
}