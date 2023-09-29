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
  - Métodos principais
    - private String gerarUuid() retorna um objeto UUID aleatório convertido em String
    - public Empresa é um construtor do objeto, asignando um valor 0 ao id, uuid do método anterior, data do relógio do sistema, e os campos procedentes do DTO no uso
    - public String toStringE() retorna os campos do objeto como uma linha de texto
    
- Pessoa: classe final que extende a classe Usuario, definindo o objeto Pessoa. O objeto criado se armazenará no banco de dados H2
  - Características do objeto e do seu respectivo campo no BD
    - id inteiro autogerado em BD
    - uuid String, em BD: length = 36, nullable = false, name = "uuid". O UUID será usado em combinaçao com o CPF como chave par única, UUID tem maior complexidade que o ID autogerado, dando mais segurança.
    - cpf String, em BD length = 11, nullable = false, name = "cpf". CPF da pessoa formatado com zeros à esquerda totalizando 11 dígitos. Usado na lógica como chave. Não é aplicado um algoritmo de validação.
    - mcc String, em BD length = 4, nullable = false, name = "mcc". MCC - “Merchant Category Code“ tem até 4 dígitos.
    - nome String, em BD length = 50, nullable = false, name = "nome". Nome da pessoa usuária, texto de até 50 caracteres.
    - email String, em BD length = 100, nullable = false, name = "email". Email do contato do estabelecimento, texto de até 100 caracteres. A validação requerida usa uma expressão regular para validação:
     "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\.]+)\\.([a-zA-Z]{2,5})$") mas os relatos dos usuários indicaram defeitos. A regex utilizada se define na DTO
    - dataCadastro LocalDateTime, em BD nullable = false, name="datacadastro". Data do cadastro gerado com a data e hora do sistema
  - Métodos principais
    - private String gerarUuid() retorna um objeto UUID aleatório convertido em String
    - public Pessoa é um construtor do objeto, asignando um valor 0 ao id, uuid do método anterior, data do relógio do sistema, e os campos procedentes do DTO no uso
    - public String toStringC() retorna os campos do objeto como uma linha de texto
- Usuario: classe com dois herdeiros.
#### DTO
- EmpresaDto: classe reduzida da clase Empresa, usada para validar os dados que foram inseridos na API
  - Características do objeto e de suas respectivas validações e RegEx. Tomam os tipos e limites dos campos análogos da classe que reduz.
    - cnpj @NotBlank(message = "CNPJ de 14 números obrigatório.") @Size(max = 14) @Pattern(regexp = "(\\d{1,14})", message = "o CNPJ nao é válido.")
    - razaosocial @NotBlank(message = "Razao social obrigatória.")
    - mcc @NotBlank(message = "MCC obrigatório.") @Pattern(regexp = "\\b([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])\\b")
    - cpf @NotBlank(message = "CPF do contato obrigatório.") @Size(max = 11) @Pattern(regexp = "(\\d{1,11})", message = "o CNPJ nao é válido.")
    - nomecontato @NotBlank(message = "Nome do contato obrigatório.")
    - email @NotBlank(message = "Email obrigatório.") @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$", flags = {Flag.CASE_INSENSITIVE, Flag.MULTILINE}, message = "o email nao é válido.")
  - Método principal
    - public toEmpresa() retorna um objeto do tipo Empresa passando os campos depois da validação (cnpj, razaosocial, mcc, cpf, nomecontato, email)
- PessoaDto: classe reduzida da clase Pessoa, usada para validar os dados que foram inseridos na API
    - Características do objeto e de suas respectivas validações e RegEx. Tomam os tipos e limites dos campos análogos da classe que reduz.
    - mcc @NotBlank(message = "MCC obrigatório.") @Pattern(regexp = "\\b([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])\\b")
    - cpf @NotBlank(message = "CPF da pessoa obrigatório.") @Size(max = 11) @Pattern(regexp = "(\\d{1,11})", message = "o CNPJ nao é válido.")
    - nome @NotBlank(message = "Nome da pessoa obrigatório.")
    - email @NotBlank(message = "Email obrigatório.") @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$", flags = {Flag.CASE_INSENSITIVE, Flag.MULTILINE}, message = "o email nao é válido.")
  - Método principal
    - public toPessoa() retorna um objeto do tipo Pessoa passando os campos depois da validação (cpf, mcc, nome, email)

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
- Validar se o CPF atende as normas brasileiras de contruçao de tal cadastro
- Otimizar o uso de métodos comuns
- Aperfeiçoar os testes unitários
## [Desafio 1:](../../tree/main/desafio1)
