package imdl.eclesia.domain;

public enum LogAction {
    CRIAR_ESCALA("Criou Escala"),
    ATUALIZAR_ESCALA("Atualizou Escala"),
    DEFINIR_MUSICAS("Definiu Músicas na Escala"),
    EXCLUIR_ESCALA("Excluiu Escala"),
    ADICIONAR_LEVITA("Adicionou Levita"),
    REMOVER_LEVITA("Removeu Levita");

    private final String acao;

    LogAction(String acao) {
        this.acao = acao;
    }

    public String getAcao() {
        return acao;
    }
}
