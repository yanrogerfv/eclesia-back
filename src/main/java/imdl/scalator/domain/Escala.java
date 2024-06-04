package imdl.scalator.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Escala {
    private UUID id;
    private String titulo;
    private LocalDate data;
    private Levita ministro;
    private Levita violao;
    private Levita teclado;
    private Levita baixo;
    private Levita bateria;
    private List<Levita> back;
    private List<Musica> musicas;
    private String observacoes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Levita getMinistro() {
        return ministro;
    }

    public void setMinistro(Levita ministro) {
        this.ministro = ministro;
    }

    public Levita getViolao() {
        return violao;
    }

    public void setViolao(Levita violao) {
        this.violao = violao;
    }

    public Levita getTeclado() {
        return teclado;
    }

    public void setTeclado(Levita teclado) {
        this.teclado = teclado;
    }

    public Levita getBaixo() {
        return baixo;
    }

    public void setBaixo(Levita baixo) {
        this.baixo = baixo;
    }

    public Levita getBateria() {
        return bateria;
    }

    public void setBateria(Levita bateria) {
        this.bateria = bateria;
    }

    public List<Levita> getBack() {
        return back;
    }

    public void setBack(List<Levita> back) {
        this.back = back;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
