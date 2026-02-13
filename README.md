# 📌 Fórum API — RESTful Backend com Spring Boot + JWT

API RESTful para gerenciamento de sistema de fórum baseado no forum da Alura. 

Funções:
- autenticação de usuários
- criação de tópicos
- publicação de respostas
- definição de respostas aceitas como solução.

O projeto foi desenvolvido como challenge proposto pela Alura no programa ONE.

---

## 🚀 Tecnologias

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- Spring Security
- JWT (JSON Web Token) com auth0
- PostgreSQL
- Flyway (migrations)
- Bean Validation

---

## 🏗 Arquitetura

Estrutura baseada em camadas:

Controller | Service | Infra | Domain

### Responsabilidades

- **Controllers** → exposição HTTP/REST
- **Services** → regras de negócio e autorização
- **Repositories** → acesso a dados (JPA)
- **Entities (Domain)** → modelo de domínio com comportamento
- **DTOs** → desacoplamento entre API e entidades
- **Security** → autenticação JWT e filtros
- **Migrations** → versionamento do schema

### Benefícios

✔ baixo acoplamento  
✔ coesão  
✔ testabilidade  
✔ manutenção facilitada  

---

## 📚 Domínio

### 👤 Usuário
- Autor de tópicos e respostas
- Implementa `UserDetails`
- Senhas com hash BCrypt

### 📝 Tópico
- Discussão criada por usuário
- Possui respostas associadas
- Encapsula comportamentos de atualização
- `orphanRemoval = true` para evitar registros órfãos

### 💬 Resposta
- Vinculada a tópico e autor
- Pode ser marcada como solução
- Apenas uma solução por tópico

---

## 🔐 Segurança

Autenticação stateless com JWT.

### Fluxo

1. `POST /login` → autentica usuário  
2. JWT é gerado  
3. Cliente envia token no header `Authorization`  
4. Filtro valida token a cada requisição  
5. Usuário disponível via `@AuthenticationPrincipal`  

---

## 🔗 Endpoints principais

### 🔑 Autenticação
```
POST /login
```

### 📝 Tópicos
```
POST   /topico
GET    /topico
GET    /topico/{id}
PUT    /topico/{id}
DELETE /topico/{id}
```

### 💬 Respostas
```
POST   /resposta
PUT    /resposta/{id}
PUT    /resposta/{id}/solucao
DELETE /resposta/{id}/solucao
DELETE /resposta/{id}
```

---

## ⚙️ Funcionalidades implementadas

- Autenticação JWT stateless
- Autorização baseada no autor
- CRUD completo de tópicos e respostas
- Marcação de resposta como solução
- Regra de única solução por tópico
- Paginação
- DTO Pattern
- Bean Validation
- Migrations versionadas (Flyway)
- Encapsulamento do domínio
- Integridade referencial

---

## 🧠 Regras de negócio gerais

- Apenas autor do tópico edita/exclui tópico
- Apenas autor da resposta edita resposta
- Somente autor do tópico marca solução de respostas
- Apenas 1 solução por tópico
- Autenticação obrigatória em rotas protegidas

---

## 📌 Melhorias planejadas

- Documentação automática com SpringDoc / OpenAPI
- Tratamento global de erros (ExceptionHandler)
- Cadastro de usuários
- Perfis/roles de autorização
- Testes automatizados

---

## 🎯 Objetivo

Este projeto demonstra a construção de uma API backend, aplicando padrões utilizados em sistemas de TI com foco em:

- arquitetura limpa
- segurança moderna
- domínio rico
- escalabilidade
- fácil manutenção



