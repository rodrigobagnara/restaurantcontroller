# 🍽️ API de Controle de Restaurantes

Uma API RESTful construída com **Spring Boot** para gerenciar usuários e endereços de restaurantes, com validação abrangente, configuração de segurança e documentação automatizada.

---

## 🧭 Visão Geral da Arquitetura

Este projeto implementa uma **API REST Spring Boot** com as seguintes funcionalidades:

- Gerenciamento de usuários com operações CRUD completas
- Gerenciamento de endereços vinculados a usuários
- Criptografia de senhas usando BCrypt
- Validação de dados com Bean Validation
- Tratamento de exceções com exceções personalizadas
- Documentação OpenAPI/Swagger
- Integração com banco de dados PostgreSQL

---

### 🧩 Endpoints da API

#### 1️⃣ Verificação de Saúde
- **GET** `/ping` - Retorna o status de saúde do serviço

#### 2️⃣ Gerenciamento de Usuários
- **POST** `/users` - Criar novo usuário (com endereço e credenciais)
- **GET** `/users` - Listar todos os usuários
- **GET** `/users/{id}` - Buscar usuário por ID
- **GET** `/users/search?name={name}` - Buscar usuários por nome (parcial, case insensitive)
- **PUT** `/users/{id}` - Atualizar dados do usuário (exceto credenciais)
- **DELETE** `/users/{id}` - Deletar usuário por ID

#### 3️⃣ Gerenciamento de Endereços
- **GET** `/addresses/user/{userId}` - Buscar endereço por ID do usuário
- **PUT** `/addresses/user/{userId}` - Atualizar endereço do usuário

---

### ⚙️ Stack Tecnológico

| Componente        | Tecnologia              |
|------------------|-------------------------|
| Linguagem        | Java 21                 |
| Framework        | Spring Boot 3.5.7       |
| Banco de Dados   | PostgreSQL              |
| Build            | Maven                   |
| Containerização  | Docker & Docker Compose |

---

## 🚀 Executando o Projeto

### 🧰 Pré-requisitos

Certifique-se de ter instalado:

- Docker & Docker Compose

---

### ▶️ Iniciar a Infraestrutura

A partir da raiz do projeto, execute:

```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL (porta 5432)
- API Restaurant Controller (porta 8080)

---

## 🔧 Variáveis de Ambiente

TODO -> Será feito a partir do docker-compose.yml

---

## 🧩 Acessos de Gerenciamento

**Documentação da API**:
- Interface Swagger/OpenAPI disponível em: http://localhost:8080/swagger-ui.html
- JSON OpenAPI: http://localhost:8080/v3/api-docs

---

## 🔒 Autenticação

A API utiliza autenticação HTTP Basic baseada em credenciais armazenadas no banco de dados. Para cada requisição autenticada, o sistema verifica o usuário e senha informados no header Authorization contra a tabela `user_credentials` do banco de dados.

- Por padrão, um usuário administrador é criado na inicialização do sistema:
  - Usuário: `ADMIN`
  - Senha: `ADMIN`
- O provedor de autenticação (`CustomAuthenticationProvider`) foi implementado para validar as credenciais usando o Spring Security.
- Endpoints públicos (como `/ping` e documentação Swagger) não exigem autenticação.
- Para acessar endpoints protegidos, envie o header HTTP:

```
Authorization: Basic <base64(username:password)>
```

Caso as credenciais estejam incorretas, a API retorna HTTP 401 Unauthorized.

---

## 📬 Coleção de requisições Postman

TODO -> Apontar para a coleção do Postman que será criada em breve.