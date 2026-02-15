package imdl.eclesia.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String ministroNome;
    private Levita baixo;
    private String baixoNome;
    private Levita bateria;
    private String bateriaNome;
    private Levita guitarra;
    private String guitarraNome;
    private Levita teclado;
    private String tecladoNome;
    private Levita violao;
    private String violaoNome;
    private List<Levita> back;
    private List<Musica> musicas;
    private String observacoes;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public Escala update(Escala escala) {
        this.titulo = escala.titulo;
        this.data = escala.data;
        this.observacoes = escala.observacoes;

        this.quarta = escala.quarta;
        this.domingo = escala.domingo;
        this.especial = escala.especial;

        if (escala.ministro != null) {
            this.ministro = escala.ministro;
            this.ministroNome = escala.ministroNome;
        } if (escala.violao != null) {
            this.violao = escala.violao;
            this.violaoNome = escala.violaoNome;
        } if (escala.teclado != null) {
            this.teclado = escala.teclado;
            this.tecladoNome = escala.tecladoNome;
        } if (escala.bateria != null) {
            this.bateria = escala.bateria;
            this.bateriaNome = escala.bateriaNome;
        } if (escala.baixo != null) {
            this.baixo = escala.baixo;
            this.baixoNome = escala.baixoNome;
        } if (escala.guitarra != null) {
            this.guitarra = escala.guitarra;
            this.guitarraNome = escala.guitarraNome;
        }

        this.back = escala.back;
        this.musicas = escala.musicas;

        return this;
    }

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

    public String getMinistroNome() {
        return ministroNome;
    }

    public void setMinistroNome(String ministroNome) {
        this.ministroNome = ministroNome;
    }

    public String getBaixoNome() {
        return baixoNome;
    }

    public void setBaixoNome(String baixoNome) {
        this.baixoNome = baixoNome;
    }

    public String getBateriaNome() {
        return bateriaNome;
    }

    public void setBateriaNome(String bateriaNome) {
        this.bateriaNome = bateriaNome;
    }

    public String getGuitarraNome() {
        return guitarraNome;
    }

    public void setGuitarraNome(String guitarraNome) {
        this.guitarraNome = guitarraNome;
    }

    public String getTecladoNome() {
        return tecladoNome;
    }

    public void setTecladoNome(String tecladoNome) {
        this.tecladoNome = tecladoNome;
    }

    public String getViolaoNome() {
        return violaoNome;
    }

    public void setViolaoNome(String violaoNome) {
        this.violaoNome = violaoNome;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
