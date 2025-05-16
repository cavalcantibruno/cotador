# Simulador de Crédito

## Instruções de setup.

>[!PREREQUISITES]
> 
> Esse é um projeto simples desenvolvido em Java (Maven) com Spring Boot.
> - Java Version: 17
> - Spring Boot Version: 3.4.5

Parar rodar o projeto local é interessante que você tenha alguma IDE instalada e configurada 
na máquina. Pode ser um [IntelliJ](https://www.jetbrains.com/pt-br/idea/), [VSCode](https://code.visualstudio.com/download) ou outra de sua preferência. 

_Sugestão: O **IntelliJ IDEA** oferece um rico conjunto de ferramentas de desenvolvimento integradas e suporte ao framework Spring, 
tanto para código em Java quanto em Kotlin, incluindo Spring MVC, Spring Boot, Spring Integration, Spring Security e Spring Cloud._     

Para executar o projeto com **Docker**, utilize o comando abaixo:

~~~Bash
docker-compose up --build
~~~

## Exemplos de requisições para os endpoints.

### Endpoint: `/api/v1/cotacao/sync/simular`

- **Método:** `POST`
- **Descrição:** Realiza simulação de emprestimo de forma sincrona.

### Requisição

```json
{
  "valorCredito": 50000,
  "dataNascimento": "1985-09-20",
  "prazo": 24,
  "email": "anakin.skywalker@outlook.com",
  "nome": "Anakin "
}
```

### Resposta

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

### Endpoint: `/api/v1/cotacao/async/simular`

- **Método:** `POST`
- **Descrição:** Realiza simulação de emprestimo em lote de forma assincrona.

### Requisição

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

### Resposta

```json
{
  "status": "SUCESSO",
  "mensagem": "Solicitação realizada com sucesso"
}
```

## Explicação sobre a estrutura do projeto e decisões de arquitetura.

## TODO

-[X] Crie endpoints para: Simular um empréstimo. (Síncrono e Assíncrono)
-[X] Documentação Swagger (OpenAPI)
-[ ] Criação dos testes automatizados e de integração
-[ ] Executar teste de desempenho
-[ ] Submeta o código em um repositório Git

### Bônus

-[X] Implementar notificação por email com os resultados da simulação.
-[ ] Adicionar suporte para diferentes cenários de taxa de juros (fixa e variável).
-[X] Criar um Dockerfile e docker-compose para facilitar o setup da aplicação.
-[X] Adicionar suporte para diferentes moedas e conversão de taxas.