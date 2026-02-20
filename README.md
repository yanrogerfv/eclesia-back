# Eclesia - Logs (Eventos)

Este projeto salva logs de alterações via eventos do Spring usando uma única tabela de logs.

## O que foi adicionado

- Evento de domínio `LevitaChangedEvent`.
- Listener `LevitaLogListener` que persiste logs em `log`.
- Entidade única de log (`LogEntity`) com `tipoLog`.
- Enum `TipoLog` com valores para Escala, Levita, Música e Instrumento.

## Como funciona

Quando um levita ou uma escala é criada/atualizada/removida, a aplicação registra um log com:

- `referenciaId` (id da entidade)
- `tipoLog` (ESCALA, LEVITA, etc)
- `action`
- `description`

## Pontos de extensão

- Adicionar novos tipos em `TipoLog`.
- Reusar `LogRepository` e `LogMapper` para novas entidades (Músicas, Instrumentos).

## Observações

O projeto usa Spring Boot e JPA. O `ddl.sql` contém a tabela `log` unificada.
