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
- Empresa: classe final que extende a classe Usuario, definindo o objeto Empresa. O objeto criado se armazenará no banco de dados H2
  - Características do objeto e do seu respectivo campo no BD
    - id inteiro autogerado em BD
    - uuid String, em BD: length = 36, nullable = false, name = "uuid". O UUID será usado em combinaçao com o CNPJ como chave par única, UUID tem maior complexidade que o ID autogerado, dando mais segurança.
    - cnpj String, en BD length = 14, nullable = false, name = "cnpj". O requerimento é que seja numérico, formatado com zeros à esquerda, utilizando 14 caracteres. Usado na lógica como chave.
    Representa o CNPJ de uma empresa. Não é aplicado um algoritmo de validação.
    - razaosocial String, em BD length = 50, nullable = false, name = "razaosocial". Razão Social da empresa
    - mcc String, em BD length = 4, nullable = false, name = "mcc". MCC - “Merchant Category Code“ tem até 4 dígitos.
    - cpf String, em BD length = 11, nullable = false, name = "cpf". CPF do contato formatado com zeros à esquerda totalizando 11 dígitos. Não é aplicado um algoritmo de validação.
    - nomecontato String, em BD length = 50, nullable = false, name = "nomecontato". Nome do contato do estabelecimento, texto de até 50 caracteres.
    - email String, em BD length = 100, nullable = false, name = "email". Email do contato do estabelecimento, texto de até 100 caracteres. A validação requerida usa uma expressão regular para validação:
     "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\.]+)\\.([a-zA-Z]{2,5})$") mas os relatos dos usuários indicaram defeitos. A regex utilizada se define na DTO
    - dataCadastro LocalDateTime, em BD nullable = false, name="datacadastro". Data do cadastro gerado com a data e hora do sistema
    
    private String gerarUuid(){
        return UUID.randomUUID().toString();
    }
- Pessoa
- Usuario
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
- Validar se o CNPJ atende as normas brasileiras de contruçao de tal cadastro
- ///
- Aperfeiçoar os testes unitários
- Aperfeiçoar as provas unitárias
## [Desafio 1:](../../tree/main/desafio1)
