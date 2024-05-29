package imdl.scalator.domain.input;

import imdl.scalator.domain.Instrumento;
import lombok.Data;

import java.util.UUID;

@Data
public class LevitaInput {
    private String nome;
    private Integer instrumento;
    private String contato;
    private String email;
    private boolean disponivel;
}
