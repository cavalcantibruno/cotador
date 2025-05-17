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
- **Parâmetros:** taxa _(Opcional)_ 

> **Exemplos de parâmetros:**
> * _TAXA_FIXA_ 
> * _TAXA_SELIC_
> * _TAXA_CDI_

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

Para este projeto, optei por desenvolver uma API utilizando o padrão MVC (Model-View-Controller), com o objetivo de separar as responsabilidades da aplicação. Como manipulação de dados, lógica de negócios e tratamento de requisições e respostas. Essa abordagem torna o código mais organizado, testável e de fácil manutenção.

No contexto de APIs, a aplicação do padrão MVC foca principalmente nos models e controllers, já que a view é tradicionalmente responsável pela interface visual torna-se menos relevante, uma vez que as APIs geralmente retornam dados no formato JSON, em vez de renderizar interfaces gráficas.

📁 api 
* Interage com o modelo para realizar as operações necessárias.
* Formata a resposta da API, geralmente em formato JSON, e envia de volta ao cliente.
* Mapeia as requisições para as ações corretas do modelo, controlando o fluxo da aplicação.
~~~
    └── exceptionhandler : Centralizar o tratamento de exceções  - Armazena classes responsáveis por capturar e tratar exceções de forma padronizada em toda a aplicação.
    └── v1 : Versão da API.
       └── controller : Recebe as requisições (HTTP requests) da API.
       └── dto : Os DTOs são usados para encapsular e transferir dados entre diferentes camadas (por exemplo, entre o controller e o service), evitando expor diretamente os modelos da base de dados.
~~~

📁 config 
* Responsavél por armazenar configurações da aplicação
* Contém arquivos responsáveis por centralizar as definições que controlam o comportamento da aplicação.
~~~
    └── filter : Separar responsabilidades transversais (cross-cutting concerns) Funções que afetam várias partes da aplicação (como CORS, compressão, monitoramento, etc.) são implementadas como filtros e organizadas nesta pasta.
    └── springdoc : Armazena classes e arquivos relacionados à documentação automática da API usando o SpringDoc, uma integração do Spring Boot com OpenAPI (anteriormente Swagger).
~~~

📁 domain
* Representa os dados da aplicação, geralmente modelados em classes que refletem a estrutura dos dados a serem manipulados pela API.
* Contém a lógica para interagir com o banco de dados, recuperar, salvar, atualizar ou excluir informações.
* Pode incluir validações de dados e regras de negócio relacionadas à manipulação dos dados.
~~~
    └── enums : Centralizar definições de constantes nomeadas
    └── service : Encapsular a lógica de negócio da aplicação - A classe service concentra regras, cálculos, validações e qualquer outra lógica que não pertence diretamente ao controller nem ao repositório.
~~~

📁 utils
* Centralizar funções utilitárias - Armazena métodos ou classes com funcionalidades genéricas e reutilizáveis que não pertencem diretamente a uma camada específica do sistema.
* Evitar repetição de código - Promove o reuso de lógica comum, como formatação de datas, validações simples, conversões, entre outros.

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
- [X] Adicionar suporte para diferentes cenários de taxa de juros (fixa e variável)
- [x] Criar um Dockerfile e docker-compose para facilitar o setup da aplicação
- [x] Adicionar suporte para diferentes moedas e conversão de taxas
