package imdl.eclesia.domain.input;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class EscalaInput {
    private UUID id;
    private LocalDate data;
    private boolean especial;
    private String titulo;
    private UUID ministro;
    private UUID baixo;
    private UUID bateria;
    private UUID guitarra;
    private UUID teclado;
    private UUID violao;
    private List<UUID> backs;
    private String observacoes;
}
