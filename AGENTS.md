# Guia de Orientação para Agentes de IA (AGENTS.md)

Este documento serve como diretriz para agentes de inteligência artificial que realizarem manutenção, refatoração ou adição de novas funcionalidades neste repositório. Siga rigorosamente os padrões de arquitetura e design estabelecidos abaixo.

---

## 1. Arquitetura do Projeto

O projeto segue uma arquitetura em camadas bem definida, visando baixo acoplamento e separação de responsabilidades. Não misture as camadas.

### Camadas:
1. **Controller**: Apenas expõe os endpoints HTTP, gerencia requisições, respostas e valida autorizações com Spring Security.
   - *Exemplo*: [EscalaController.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/controller/EscalaController.java)
2. **Service (Domínio & Regras)**: Contém toda a lógica de negócio pura do sistema.
   - *Exemplo*: [EscalaService.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/service/EscalaService.java)
3. **Repository (Persistência)**: Interfaces estendendo `JpaRepository`.
   - *Exemplo*: [EscalaRepository.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/persistence/EscalaRepository.java)
4. **Mappers**: Classes utilitárias estáticas que fazem a tradução mútua entre Entidades JPA (banco) e Classes de Domínio (negócio).
   - *Exemplo*: [EscalaMapper.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/service/mapper/EscalaMapper.java)
5. **Entity**: Representação física no banco de dados.
   - *Exemplo*: [EscalaEntity.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/entity/EscalaEntity.java)

---

## 2. Regras Críticas de Desenvolvimento

### Proibido o uso de `@Service` ou `@Component` em Classes de Serviço
Neste projeto, as classes de serviço **não** são anotadas diretamente com `@Service` ou `@Autowired`. Em vez disso, elas são declaradas explicitamente como `@Bean` em classes de configuração dedicadas.
- Ao criar um novo serviço, registre-o no pacote de configuração correspondente.
- *Referências de Configurações*:
  - [EscalaConfig.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/configuration/EscalaConfig.java)
  - [LevitaConfig.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/configuration/LevitaConfig.java)
  - [UserConfig.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/auth/configuration/bean/UserConfig.java)

### Logging desacoplado por Eventos do Spring
Nunca injete o repositório de logs em serviços de domínio ou escreva strings de auditoria diretamente nas regras de negócio.
- O sistema de log é baseado em eventos. Publique um evento estendendo a estrutura do Spring.
- O serviço publica o evento com `ApplicationEventPublisher`.
- O [LogListener.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/service/utils/events/LogListener.java) captura o evento automaticamente e persiste na tabela unificada de logs.
- Para novas entidades a serem logadas, siga o passo a passo no [Logs by Events.md](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/Logs%20by%20Events.md).

### Segurança e Papéis de Acesso (RBAC)
Ao criar endpoints que alteram dados (`POST`, `PUT`, `DELETE`), sempre verifique se o usuário possui os papéis autorizados (como `ADMIN` ou `Líder`).
- Faça a verificação de segurança no Controller inspecionando as authorities do `SecurityContextHolder`.
- Garanta que rotas públicas sejam configuradas corretamente no [SecurityConfig.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/auth/configuration/SecurityConfig.java) e no [JwtAuthenticationFilter.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/auth/filter/JwtAuthenticationFilter.java).

### Mapeamento JSONB
A tabela unificada de log usa o tipo `jsonb` do PostgreSQL para guardar o snapshot serializado do domínio.
- No JPA, mapeie campos JSON usando `@JdbcTypeCode(SqlTypes.JSON)` com Jackson `JsonNode`. Veja o exemplo em [LogEntity.java](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/java/imdl/eclesia/entity/LogEntity.java).

---

## 3. Passos para Criar uma Nova Entidade / Funcionalidade

1. Crie a tabela correspondente no arquivo [ddl.sql](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/resources/ddl.sql).
2. Crie a classe de Domínio em `imdl.eclesia.domain`.
3. Crie a entidade JPA em `imdl.eclesia.entity`.
4. Crie a interface de Repositório em `imdl.eclesia.persistence`.
5. Crie a classe Mapper em `imdl.eclesia.service.mapper`.
6. Crie o Serviço sem anotações de Spring em `imdl.eclesia.service`.
7. Registre o serviço como um `@Bean` no pacote `imdl.eclesia.configuration`.
8. Crie o Controller REST anotado com `@RestController` e `@RequestMapping` no pacote `imdl.eclesia.controller`.
