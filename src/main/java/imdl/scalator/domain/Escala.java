package imdl.scalator.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Escala {
    private UUID id;
    private LocalDate data;
    private String titulo;
    private boolean quarta;
    private boolean domingo;
    private boolean especial;
    private Levita ministro;
    private Levita baixo;
    private Levita bateria;
    private Levita guitarra;
    private Levita teclado;
    private Levita violao;
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

    public boolean isQuarta() {
        return quarta;
    }

    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
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

    public Levita getGuitarra() {
        return guitarra;
    }

    public void setGuitarra(Levita guitarra) {
        this.guitarra = guitarra;
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
