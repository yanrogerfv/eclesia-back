package imdl.eclesia.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LevitaResumed {
    private UUID id;
    private String nome;
    private String email;
    private String contato;
    private String descricao;
    private List<LocalDate> agenda;
    private List<Instrumento> instrumentos;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<LocalDate> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<LocalDate> agenda) {
        this.agenda = agenda;
    }

    public List<Instrumento> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(List<Instrumento> instrumentos) {
        this.instrumentos = instrumentos;
    }
}
