# Sistema de Logs Unificado com Eventos

Este projeto implementa um sistema de logging baseado em eventos Spring que captura mudanças em **Escalas, Levitas, Músicas e Instrumentos**.

## Arquitetura

### Componentes Principais

1. **Eventos de Domínio** (`domain/event/`)
   - `EscalaEvent` - Evento disparado ao criar/atualizar/deletar escalas
   - `LevitaEvent` - Evento disparado ao criar/atualizar/deletar levitas
   - `MusicaEvent` - Evento disparado ao criar/atualizar/deletar músicas
   - `InstrumentoEvent` - Evento disparado ao criar/atualizar/deletar instrumentos

2. **Serviço de Logging** (`service/utils/events/`)
   - `LogListener` - Listener unificado que captura todos os eventos
   - `LogAction` - Enum com todas as ações loggáveis
   - `TipoLog` - Enum para classificar o tipo de log (ESCALA, LEVITA, MUSICA, INSTRUMENTO)
   - `Log` - Classe de domínio para representar um log

3. **Persistência**
   - `LogRepository` - Repository JPA para salvar logs na tabela `log`
   - `LogMapper` - Mapper para converter Log de domínio para entidade JPA

## Fluxo de Operação

```
Serviço (ex: EscalaService, MusicaService, InstrumentoService, LevitaService)
    ↓
publishEvent(new XEvent(...))
    ↓
LogListener.handleXEvent()
    ↓
LogMapper.domainToEntity()
    ↓
LogRepository.save()
    ↓
Tabela `log` no banco
```

## Modelo de Dados

A tabela `log` possui os seguintes campos:

```sql
log_id         UUID (Primary Key)
referencia_id  UUID (ID da entidade que foi modificada)
tipo_log       VARCHAR (ESCALA, LEVITA, MUSICA, INSTRUMENTO)
action         VARCHAR (Descrição da ação em português)
description    TEXT (Descrição formatada com usuário, ação, entidade e timestamp)
object         TEXT (Representação em string do objeto para auditoria)
```

## Tipos de Ações

### Escala
- `CRIAR_ESCALA`
- `ATUALIZAR_ESCALA`
- `EXCLUIR_ESCALA`
- `DEFINIR_MUSICAS`

### Levita
- `CRIAR_LEVITA`
- `ATUALIZAR_LEVITA`
- `EXCLUIR_LEVITA`
- `ADICIONAR_INSTRUMENTO` (quando instrumento é adicionado a um levita)
- `REMOVER_INSTRUMENTO` (quando instrumento é removido de um levita)
- `DEFINIR_AGENDA`

### Música
- `CRIAR_MUSICA`
- `ATUALIZAR_MUSICA`
- `EXCLUIR_MUSICA`

### Instrumento
- `CRIAR_INSTRUMENTO`
- `EXCLUIR_INSTRUMENTO`

## Serviços com Eventos Implementados

### EscalaService
- Publica `EscalaEvent` em create, update e delete
- Injecta `ApplicationEventPublisher`

### LevitaService
- Publica `LevitaEvent` em create, update, delete, addInstrumento, removeInstrumento e setLevitaAgenda
- Injecta `ApplicationEventPublisher`

### MusicaService
- Publica `MusicaEvent` em create, update e delete
- Injecta `ApplicationEventPublisher`

### InstrumentoService
- Publica `InstrumentoEvent` em create e delete
- Injecta `ApplicationEventPublisher`

## Exemplo de Log Gravado

```json
{
  "logId": "550e8400-e29b-41d4-a716-446655440000",
  "referenciaId": "123e4567-e89b-12d3-a456-426614174000",
  "tipoLog": "ESCALA",
  "action": "Criou Escala",
  "description": "Usuário X Criou Escala Culto Especial do dia 15/02/2025 às 14:30:45",
  "object": "Escala(...)"
}
```

## Extensibilidade

Para adicionar logs de novas entidades:

1. Criar `NovoEvent` em `domain/event/`
2. Adicionar ações em `LogAction` enum
3. Injetar `ApplicationEventPublisher` no serviço
4. Publicar evento ao final da operação
5. Adicionar handler em `LogListener`:

```java
@EventListener
public void handleNovoEvent(NovoEvent event) {
    Log logEntry = new Log();
    logEntry.setReferenciaId(event.getEntidade().getId());
    logEntry.setTipoLog(TipoLog.NOVO);
    logEntry.setAction(event.getAction().getAcao());
    logEntry.setDescription(String.format(
        "%s %s %s às %s.",
        event.getUser(),
        event.getAction().getAcao(),
        event.getEntidade().getNome(),
        LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    ));
    logEntry.setObject(event.getEntidade().toString());
    logRepository.save(LogMapper.domainToEntity(logEntry));
}
```

## Configuração

Os listeners e services são registrados como Beans nas respectivas classes de configuração:
- `EscalaConfig` - EscalaService com ApplicationEventPublisher
- `LevitaConfig` - LevitaService, LogListener com ApplicationEventPublisher
- `MusicaConfig` - MusicaService com ApplicationEventPublisher
- `InstrumentoConfig` - InstrumentoService com ApplicationEventPublisher

O `LogListener` escuta automaticamente todos os eventos disparados pelos serviços.

## Tabela DDL

```sql
CREATE TABLE log (
    log_id         uuid primary key default gen_random_uuid(),
    referencia_id  uuid,
    tipo_log       text not null,
    action         text,
    description    text,
    object         text
);
```

