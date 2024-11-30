package imdl.scalator.domain;

import java.time.LocalDate;
import java.util.UUID;

public class EscalaResumed {
    private UUID id;
    private LocalDate data;
    private String titulo;
    private boolean quarta;
    private boolean domingo;
    private boolean especial;
    private String ministro;
    private String baixo;
    private String bateria;
    private String guitarra;
    private String teclado;
    private String violao;
    private String observacoes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public String getMinistro() {
        return ministro;
    }

    public void setMinistro(String ministro) {
        this.ministro = ministro;
    }

    public String getBaixo() {
        return baixo;
    }

    public void setBaixo(String baixo) {
        this.baixo = baixo;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public String getGuitarra() {
        return guitarra;
    }

    public void setGuitarra(String guitarra) {
        this.guitarra = guitarra;
    }

    public String getTeclado() {
        return teclado;
    }

    public void setTeclado(String teclado) {
        this.teclado = teclado;
    }

    public String getViolao() {
        return violao;
    }

    public void setViolao(String violao) {
        this.violao = violao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
