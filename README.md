# Simulador de Crédito

## Instruções de Setup

> **Pré-requisitos**  
> Projeto simples desenvolvido em Java (Maven) com Spring Boot.
> - **Java Version:** 17
> - **Spring Boot Version:** 3.4.5

Para rodar o projeto localmente, é interessante que você tenha alguma IDE instalada e configurada na máquina. Pode ser o [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/), [VSCode](https://code.visualstudio.com/download) ou outra de sua preferência.

> 💡 **Sugestão:**  
> O **IntelliJ IDEA** oferece um rico conjunto de ferramentas de desenvolvimento integradas e suporte ao framework Spring, tanto para código em Java quanto em Kotlin, incluindo Spring MVC, Spring Boot, Spring Integration, Spring Security e Spring Cloud.

### Executando com Docker

Para executar o projeto com **Docker**, utilize o comando abaixo:

```bash
docker-compose up --build
```

---

## Exemplos de Requisições para os Endpoints

### Endpoint: `/api/v1/cotacao/sync/simular`

- **Método:** `POST`
- **Descrição:** Realiza simulação de empréstimo de forma síncrona.

#### Exemplo de Requisição

```json
{
  "valorCredito": 50000,
  "dataNascimento": "1985-09-20",
  "prazo": 24,
  "email": "anakin.skywalker@outlook.com",
  "nome": "Anakin"
}
```

#### Exemplo de Resposta

```json
{
  "idCotacao": "f93979e5-a345-452c-b7c3-f6e34cd0360d",
  "prazo": 24,
  "taxaJuros": 3.0,
  "jurosTotal": 1577.44,
  "valorSolicitado": 50000.00,
  "valorParcelaMensal": 2149.06,
  "valorTotalComJuros": 51577.44
}
```

---

### Endpoint: `/api/v1/cotacao/async/simular`

- **Método:** `POST`
- **Descrição:** Realiza simulação de empréstimo em lote de forma assíncrona.

#### Exemplo de Requisição

```json
[
  {
    "valorCredito": 12000,
    "dataNascimento": "1994-08-19",
    "prazo": 24,
    "email": "ow.kenobi@gmail.com",
    "nome": "Obi-Wan"
  },
  {
    "valorCredito": 90000,
    "dataNascimento": "1966-10-26",
    "prazo": 48,
    "email": "han_solo@icloud.com",
    "nome": "Han"
  },
  {
    "valorCredito": 35000,
    "dataNascimento": "2000-04-15",
    "prazo": 18,
    "email": "padmeamidala84@outlook.com",
    "nome": "Padmé"
  }
]
```

#### Exemplo de Resposta

```json
{
  "status": "SUCESSO",
  "mensagem": "Solicitação realizada com sucesso"
}
```

---

## Estrutura do Projeto e Decisões de Arquitetura

**

---

## TODO

### Funcionalidades

- [x] Criar endpoints para simular um empréstimo (Síncrono e Assíncrono)
- [x] Documentação Swagger (OpenAPI)
- [ ] Criação dos testes automatizados e de integração
- [ ] Executar teste de desempenho
- [ ] Submeter o código em um repositório Git

### Bônus

- [x] Implementar notificação por email com os resultados da simulação
- [ ] Adicionar suporte para diferentes cenários de taxa de juros (fixa e variável)
- [x] Criar um Dockerfile e docker-compose para facilitar o setup da aplicação
- [x] Adicionar suporte para diferentes moedas e conversão de taxas
