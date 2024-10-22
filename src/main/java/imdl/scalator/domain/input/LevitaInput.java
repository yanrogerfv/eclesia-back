package imdl.scalator.domain.input;

import lombok.Data;

import java.util.List;

@Data
public class LevitaInput {
    private String nome;
    private String email;
    private String contato;
    private List<Long> instrumentos;
}
