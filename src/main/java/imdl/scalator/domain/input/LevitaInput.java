package imdl.scalator.domain.input;

import lombok.Data;

import java.util.List;

@Data
public class LevitaInput {
    private String nome;
    private List<Long> instrumentos;
    private String contato;
    private String email;
    private boolean disponivel;
}
