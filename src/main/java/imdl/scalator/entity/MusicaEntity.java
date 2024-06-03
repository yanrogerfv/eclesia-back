package imdl.scalator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class MusicaEntity {
    @Id
    @Column(name = "musica_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID musicaId;
    private String nome;
    private String link;
    private UUID escala;
}