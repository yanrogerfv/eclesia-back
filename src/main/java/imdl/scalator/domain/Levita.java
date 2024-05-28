package imdl.scalator.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Levita {
    private UUID id;
    private String nome;
    private Instrumento instrumento;
    private String contato;
    private String email;
    private boolean disponivel;
}
