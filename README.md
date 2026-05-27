# Eclesia Backend

> **API REST robusta para organização, escala e gestão de voluntários e equipes de música/louvor.**

O **Eclesia** é uma solução completa projetada para gerenciar a escala de instrumentistas e vocalistas (levitas), gerenciar as músicas e cifras do repertório, acompanhar a agenda de indisponibilidade de voluntários e garantir a segurança das operações com auditorias automatizadas via eventos.

---

## Tecnologias Utilizadas

O ecossistema técnico do Eclesia utiliza práticas modernas de desenvolvimento:
* **Java 17** e **Spring Boot 3.3.0**
* **Spring Security** com **JWT (JSON Web Token)**
* **Spring Data JPA** com suporte a **PostgreSQL** e **H2 (Banco de dados em memória)**
* **Hibernate 6** com mapeamento direto de tipos complexos para **Postgres JSONB**
* **Spring Application Events** (desacoplamento e auditoria)
* **Springdoc-OpenAPI** para documentação Swagger
* **Spring Mail** para envio de notificações por e-mail
* **Docker** e **Fly.io** para conteinerização e deploy

---

## Principais Funcionalidades

* **Escalas Inteligentes**: Criação de escalas de cultos semanais e especiais vinculando Ministro (líder do louvor), músicos nas posições de Violão, Guitarra, Teclado, Baixo, Bateria e Backings Vocals. O sistema impede automaticamente a escalação de voluntários indisponíveis.
* **Gestão de Levitas e Instrumentos**: Cadastro completo de músicos, vinculação de múltiplos instrumentos e dados de contato.
* **Agenda de Bloqueio**: Calendário individual onde cada voluntário pode cadastrar datas de indisponibilidade (ex: viagens), acionando validações em tempo de escalação.
* **Gestão de Músicas**: Cadastro de músicas integrando links de referência de áudio/vídeo e links de cifras.
* **Segurança RBAC**: Controle baseado em perfis de acesso (ADMIN, Líder, etc.), restringindo endpoints administrativos e fornecendo renovação de sessão automática via sliding tokens.
* **Sistema de Log Unificado**: Auditorias ricas registrando o autor, ação, data e o snapshot do objeto em formato JSON na tabela unificada de logs.

---

## Documentações Adicionais

Para aprofundar-se no funcionamento do Eclesia, consulte os guias dedicados:
* **[Guia Técnico de Desenvolvimento (AGENTS.md)](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/AGENTS.md)**: Manual obrigatório para programadores e agentes de IA sobre as convenções de arquitetura, registro manual de Beans e regras de persistência.
* **[Fluxos e Processos do Sistema (WORKFLOW.md)](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/WORKFLOW.md)**: Detalhamento operacional das regras de negócio, ativação de conta por convite/código de acesso e conflitos de agendas.
* **[Sistema de Logs e Eventos (Logs by Events.md)](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/Logs%20by%20Events.md)**: Documentação interna da modelagem de auditoria por eventos e instruções de extensibilidade.

---

## Como Executar o Projeto Localmente

### Pré-requisitos
* **Java JDK 17** ou superior instalado
* **Maven** (utiliza o Wrapper `./mvnw` incluso no repositório)

### Passos:
1. **Verifique as tabelas de banco**: O arquivo [ddl.sql](file:///c:/Users/id02810/Documents/Programming%20Projects/Eclesia/backend/src/main/resources/ddl.sql) define o esquema físico utilizado pelas entidades.
2. **Compile e baixe as dependências**:
   ```bash
   ./mvnw clean package -DskipTests
   ```
3. **Execute o servidor Spring Boot**:
   ```bash
   ./mvnw spring-boot:run
   ```
4. A API estará de pé na porta `8080`.
5. **Documentação Swagger UI**: Acesse `http://localhost:8080/swagger-ui/index.html` para visualizar e interagir com todos os endpoints expostos.
6. **H2 Console**: Acesse `http://localhost:8080/h2-console` para inspecionar as tabelas localmente.
