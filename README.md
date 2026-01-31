# 👤 Serviço de Usuário

Este projeto é um **microserviço responsável pelo gerenciamento de usuários** dentro de um ecossistema baseado em **arquitetura de microserviços** para agendamento de tarefas.

Ele centraliza todas as operações relacionadas aos usuários e é consumido por outros serviços do sistema, garantindo **separação de responsabilidades**, **escalabilidade** e **facilidade de manutenção**.

---

## 🧩 Papel no Ecossistema

Este serviço é responsável exclusivamente por:
- Cadastro de usuários
- Consulta e validação de usuários
- Atualização de dados

Ele é consumido por:
- 📅 Serviço de Agendamento de Tarefas
- 🧩 BFF (Backend for Frontend)

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Maven
- Docker
- API REST

---

## ⚙️ Funcionalidades

- Cadastro de usuário
- Consulta de usuário
- Atualização de dados
- Validações básicas de negócio

---

## ▶️ Como Executar o Projeto

### 🔹 Pré-requisitos
- Java 17+
- Maven
- Docker

---

### 🔹 Executando localmente

```bash
mvn clean spring-boot:run
