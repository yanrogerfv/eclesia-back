package imdl.eclesia.service.utils.events;

public enum LogAction {
    CRIAR_ESCALA("Criou Escala"),
    ATUALIZAR_ESCALA("Atualizou Escala"),
    DEFINIR_MUSICAS("Definiu Músicas na Escala"),
    EXCLUIR_ESCALA("Excluiu Escala"),
    CRIAR_LEVITA("Criou Levita"),
    ATUALIZAR_LEVITA("Atualizou Levita"),
    EXCLUIR_LEVITA("Excluiu Levita"),
    ADICIONAR_INSTRUMENTO("Adicionou Instrumento"),
    REMOVER_INSTRUMENTO("Removeu Instrumento"),
    DEFINIR_AGENDA("Definiu Agenda"),
    CRIAR_MUSICA("Criou Música"),
    ATUALIZAR_MUSICA("Atualizou Música"),
    EXCLUIR_MUSICA("Excluiu Música"),
    CRIAR_INSTRUMENTO("Criou Instrumento"),
    ATUALIZAR_INSTRUMENTO("Atualizou Instrumento"),
    EXCLUIR_INSTRUMENTO("Excluiu Instrumento");

    private final String acao;

    LogAction(String acao) {
        this.acao = acao;
    }

    public String getAcao() {
        return acao;
    }
}
