package imdl.scalator.domain;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Levita {
    private UUID id;
    private String nome;
    private List<Instrumento> instrumentos;
    private String contato;
    private String email;
    private boolean disponivel;
}
