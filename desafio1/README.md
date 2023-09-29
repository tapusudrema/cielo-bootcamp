# Desafio 1 do Bootcamp Cielo
## User Story: Pré-cadastro de clientes
a) modelar uma API REST com operações que possibilitem a criação, alteração, exclusão e consulta de pré-cadastros de clientes. O entregável deverá ser um documento swagger.
b) implementar na linguagem java utilizando o framework spring boot as APIs modeladas no item 1. Os dados podem ser armazenados em memória.
c) Implementar cobertura de 70% de testes unitários
### Plataforma
Spring Boot 3 / Java 17 / Maven / Tomcat / IntelliJ IDEA 2023
### Livrarias e dependências
- springfox-boot-starter
- spring-boot-starter-data-jpa: JPA (Java Persistence API) para armazenar dadod em um banco de dados relacional. Usada para criar automaticamente implementações de repositórios, de uma interfaz de repositório.
CustomerRepository extende a interfaz CrudRepository.
- spring-boot-starter-web Implementação motor web Tomcat e Restful API
- spring-boot-devtools: Ferramentas de desenvolvimento
- spring-boot-starter-test
- spring-boot-starter-validation: Validações para aplicar nos dados de entrada
- springdoc-openapi-starter-webmvc-ui
- com.h2database: h2 Banco de dados relacional
- lombok: Ferramenta para modificar
- springfox-swagger-ui: Interfaz Swagger para fazer as provas API REST
- junit & junit-jupiter-api: Para testes unitários
- modelmapper: Para usar nos DTO's
### Classes e interfazes
#### Entity
- Empresa
- Pessoa
- Usuário
#### DTO
- EmpresaDto
- PessoaDto
#### Controller
- EmpresaController
- PessoaController
#### Repository
- EmpresaRepository
- PessoaRepository
#### Service
- Desafio1Application: Inicializador da aplicação
### To-Do
- Aperfeiçoar as provas unitárias
## [Desafio 1:](../../tree/main/desafio1)
