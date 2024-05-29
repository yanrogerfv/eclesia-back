package imdl.scalator.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class MusicaEntity {
    private UUID id;
    private String nome;
    private String link;
    private UUID escala;
}