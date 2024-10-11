package imdl.scalator.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Levita {
    private UUID id;
    private String nome;
    private List<Instrumento> instrumentos;
    private String contato;
    private String email;
    private boolean disponivel;
    private List<LocalDate> agenda;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Instrumento> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(List<Instrumento> instrumentos) {
        this.instrumentos = instrumentos;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public List<LocalDate> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<LocalDate> agenda) {
        this.agenda = agenda;
    }
}
