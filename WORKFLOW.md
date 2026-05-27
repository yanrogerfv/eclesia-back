# Fluxos de Trabalho e Processos do Eclesia (WORKFLOW.md)

Este documento detalha o fluxo de processos operacionais e as regras de negócio aplicadas no sistema Eclesia. Ele serve para guiar administradores, líderes de ministério de louvor e usuários sobre como as operações funcionam na prática.

---

## 1. Fluxo de Usuários: Cadastro e Primeiro Acesso

O sistema utiliza um modelo controlado de entrada de novos membros para garantir a segurança dos dados da igreja.

```
[Líder] Cadastra Levita 
   │
   ▼
[Líder] Solicita Criação de Usuário (Inativo)
   │
   ├─► Sistema gera um "Código de Acesso" temporário
   ▼
[Músico] Recebe o Código de Acesso
   │
   ▼
[Músico] Acessa o sistema ──► Digita Código de Acesso, Nome de Usuário e Senha
   │
   ▼
[Conta Ativada!] ──► Usuário assume seu próprio login
```

### Detalhes do Processo:
1. **Cadastro do Músico (Levita)**: O líder insere os dados do músico (nome, contatos e instrumentos).
2. **Criação de Usuário Temporário**: A conta é inicialmente gerada desativada (active = false). Um username padrão é sugerido (ex: nome.sobrenome) e um Código de Acesso (Access Code) alfanumérico aleatório é gerado.
3. **Primeiro Acesso (Ativação)**: O músico acessa a página de primeiro acesso/ativação, insere o código fornecido pelo líder, define o seu nome de usuário definitivo e cria uma senha forte (mínimo de 8 caracteres). 
4. **Resgate de Senha**: Caso o músico esqueça sua senha, o líder pode restaurar o login gerando uma senha provisória e enviando uma notificação por e-mail automática para redefinição.

---

## 2. Fluxo de Gestão de Disponibilidade (Indisponibilidade)

Voluntários de ministérios de louvor muitas vezes possuem indisponibilidades em datas específicas (viagens, estudos, escala de trabalho secular).

1. **Ação do Levita**: O músico acessa o seu perfil e seleciona no calendário as datas em que não poderá tocar.
2. **Validação do Sistema**:
   - Se o músico tentar marcar como indisponível um dia em que já está escalado, o sistema bloqueia e emite um alerta avisando que ele precisa ser retirado da escala primeiro.
   - O histórico de indisponibilidades antigas (com mais de 30 dias atrás) é limpo automaticamente pelo sistema para otimização de espaço.

---

## 3. Fluxo de Montagem e Execução de Escalas

Este é o coração do sistema: organizar quem toca, quem canta e o que será tocado em cada evento da igreja.

```
       [Criar Novo Culto/Escala]
 (Define data, título, tipo de culto e observações)
                   │
                   ▼
       [Definir Ministro / Líder]
(Responsável pela condução musical do culto)
                   │
                   ▼
     [Adicionar Instrumentistas]
  (Teclado, Violão, Baixo, Bateria, Guitarra)
   * Valida se o músico selecionado está livre
                   │
                   ▼
        [Adicionar Vocalistas]
        (Lista de Backing Vocals)
   * Valida se os cantores estão livres
                   │
                   ▼
       [Definir Playlist / Músicas]
  (Vincula as músicas com links e cifras)
```

### Regras Operacionais na Montagem de Escalas:
* **Conflito de Agenda**: Ao preencher qualquer vaga instrumental ou vocal, o sistema verifica a indisponibilidade individual do músico. Caso haja conflito, a escala não é salva até que um músico disponível seja escolhido.
* **Tipos de Escalas**:
   - **Domingo** ou **Quarta**: São detectados automaticamente com base no dia da semana selecionado na data da escala.
   - **Especial**: Eventos como congressos, vigílias e batismos que ocorrem em outros dias da semana.
* **Limpeza Automática**: Para evitar acúmulo de dados obsoletos, existe um processo periódico de faxina que remove escalas muito antigas (mais de 15 dias anteriores à data atual).

---

## 4. Fluxo de Auditoria e Logs Administrativos

Para evitar alterações não autorizadas ou acidentais, todas as ações que modificam o estado de dados no sistema deixam uma trilha digital detalhada:

1. **O que é registrado?**
   - Quem realizou a ação (usuário logado).
   - Qual entidade foi afetada (Músico, Música, Instrumento ou Escala).
   - O tipo da ação (criação, edição, exclusão, adição de instrumento ou definição de agenda).
   - Um descritivo textual legível (Ex: "Usuário João alterou a Escala Culto de Domingo do dia 15/10 às 20:30:15").
   - O objeto serializado completo (estado das variáveis no momento em que ocorreu a alteração) gravado em JSON.
2. **Visualização**: Os logs servem de auditoria histórica para garantir a integridade da organização das escalas.
