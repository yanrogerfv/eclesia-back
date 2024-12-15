package imdl.scalator.controller.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LevitaFilter {
    private String nome;
    private Long instrumento;
    private LocalDate dat;
}
