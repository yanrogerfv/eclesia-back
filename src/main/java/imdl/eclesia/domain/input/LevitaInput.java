package imdl.eclesia.domain.input;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LevitaInput {
    private UUID id;
    private String nome;
    private String email;
    private String contato;
    private String descricao;
    private List<Long> instrumentos;
}
