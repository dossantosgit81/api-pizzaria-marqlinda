
# Pizzaria Marq Linda 🍕

> **"Software é para servir as pessoas."**  
Este projeto nasceu após assistir a uma live de um professor que trouxe uma reflexão sobre como as altas taxas dos aplicativos de entrega impactam pequenos negócios. Percebi que com o conhecimento que eu já tinha, poderia criar uma solução mínima para ajudar a resolver esse problema. Este foi o resultado.

---

## 📋 Sobre o projeto

**Pizzaria Marq Linda** é um sistema de pedidos desenvolvido para funcionar como uma alternativa simples e eficiente aos grandes aplicativos de delivery. O foco é oferecer uma solução de baixo custo, de fácil manutenção e que atenda diretamente às necessidades de pequenas empresas, como pizzarias e restaurantes.

---

## 🚀 Tecnologias utilizadas

- Java
- Spring Boot
- PostgreSQL
- Swagger (documentação da API)
- Docker (para facilitar o setup do ambiente)
- Flyway

---

## 📑 Documentação da API

A documentação da API está disponível via Swagger.  
Após rodar o projeto, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🛠️ Como rodar o projeto localmente

### ✅ Pré-requisitos

- [Docker](https://www.docker.com/) instalado na sua máquina.

### 🚧 Passos para rodar

1. Clone este repositório:
   ```bash
   git clone https://github.com/dossantosgit81/api-pizzaria-marqlinda.git
   cd api-pizzaria-marqlinda
   ```

2. Crie um arquivo chamado `.env` na raiz do projeto.

3. Copie os dados do arquivo `env.txt` para o `.env`.

4. Suba os containers:
   ```bash
   docker compose --env-file .env up -d
   ```

5. Crie o banco de dados(Se necessário)
    ```
    create database pizzaria_marqlinda;
   ```
6. Acesse a documentação da API (Swagger):
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

6. O banco de dados estará rodando no PostgreSQL, conforme as configurações definidas no seu `.env`.

---

## ⚙️ Variáveis de ambiente

O projeto utiliza um arquivo `.env` para configuração das variáveis sensíveis.  
Exemplo das variáveis necessárias:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=nome_do_banco
DB_USERNAME=usuario
DB_PASSWORD=senha

SPRING_PORT=8080
```

As variáveis específicas estão documentadas no arquivo `env.txt`.  
Basta criar um arquivo chamado `.env` e copiar os dados dele.

---

## 🧠 Funcionalidades principais

- 🔸 Cadastro de produtos
- 🔸 Gerenciamento de pedidos
- 🔸 Consulta de status dos pedidos(Motoboy, CHEF, Cliente)
- 🔸 Documentação interativa via Swagger
- 🔸 Banco de dados PostgreSQL rodando em container Docker
- 🔸 API REST desenvolvida em Java com Spring Boot

---

## 💡 Inspiração

> Este projeto foi inspirado na fala de um professor que destacou que **"software é para servir as pessoas"**, principalmente na busca por soluções que possam diminuir os custos e ajudar pequenos empreendedores.

---

## 📜 Licença MIT

```
MIT License

Copyright (c) 2025 Rafael Mendes

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

---

## 🙌 Contribuição

Sinta-se à vontade para contribuir, abrir issues e sugerir melhorias! Este projeto é uma iniciativa para aprendizado, colaboração e, principalmente, para ajudar pequenos negócios.

---

## ✨ Contato

Feito com 💖 por **Rafael Mendes**  
📧 [rafaelti9818@gmail.com] 

---
