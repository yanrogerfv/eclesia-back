package imdl.eclesia.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Musica {
    private UUID id;
    private String nome;
    private String link;
    private String cifra;
}
