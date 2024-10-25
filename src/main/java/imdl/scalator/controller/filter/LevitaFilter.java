package imdl.scalator.controller.filter;

import java.time.LocalDate;

public record LevitaFilter(
    String nome,
    Long instrumento,
    LocalDate date
){}
