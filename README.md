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

## 🔒 Autenticação e Login

A API utiliza autenticação baseada em credenciais armazenadas no banco de dados. Para verificar se um usuário existe e se a senha está correta, utilize o endpoint de login:

### Endpoint de Login

- **POST** `/credentials/login`
- **Body:**
  ```json
  {
    "username": "seuUsuario",
    "password": "suaSenha"
  }
  ```
- **Respostas:**
  - `200 OK`: Login realizado com sucesso!
  - `401 Unauthorized`: Credenciais inválidas
  - `500 Internal Server Error`: Erro inesperado

> **Nota:** O login compara a senha informada com o hash armazenado usando BCrypt, garantindo segurança no processo de autenticação.

---

## 📬 Coleção de requisições Postman

TODO -> Apontar para a coleção do Postman que será criada em breve.