package imdl.scalator.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Escala {
    private UUID id;
    private Levita ministro;
    private Levita violao;
    private Levita teclado;
    private Levita baixo;
    private Levita bateria;
    private List<Levita> back;
    private List<Musica> musicas;
    private LocalDate date;
    private String observacoes;
}
