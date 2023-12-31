# Desafio 4 do Bootcamp Cielo [(derivado do Desafio 3)](../desafio3/README.md)
## Technical Debt: Segurança da Informação
a) identifique um débito técnico de Segurança da Informação na aplicação
b) detalhe o débito técnico identificado, informando a criticidade e possíveis consequências
c) planeje as atividades técnicas para o desenvolvimento da solução
d) implemente a solução
### Planejamento
Foi identificado como um débito de segurança a exposiçao aos dados de pessoas e empresas, por ser uma API de livre acesso. Para dar uma camada de segurança, e tentar atender os requerimentos da LGPD, foi desenvolvida uma soluçao de login de usuário e senha para acessar aos métodos que retornam informaçao sensível. Foi usado Spring Security de Spring Boot 3.
### Plataforma
Spring Boot 3 / Java 17 / Maven / Tomcat / IntelliJ IDEA 2023
### Interfaz
Para provar a aplicação, se entra na consola Swagger, via http://localhost:8080/swagger-ui/index.html
### Livrarias e dependências
- springfox-boot-starter
- spring-boot-starter-data-jpa: JPA (Java Persistence API) para armazenar dados em um banco de dados relacional. Usada para criar automaticamente implementações de repositórios, de uma interfaz de repositório.
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
- spring-cloud-aws-dependencies: Dependência de Amazon Web Services
- spring-cloud-aws-starter-sqs: Dependência de Amazon Web Services para usar SQS
- software.amazon.awssdk: Dependência de Amazon Web Services
- spring-boot-starter-security: Para implementar métodos de segurança
- spring-boot-starter-thymeleaf: Para implementar métodos de segurança
## Foram adicionados os parámetros de conexao com AWS no arquivo application.properties do desafio 3
### Classes e interfazes
#### Entity
- Empresa: classe final que extende a classe Usuario, definindo o objeto Empresa. IDÊNTICA À DO DESAFIO 3.
- Pessoa: classe final que extende a classe Usuario, definindo o objeto Pessoa. IDÊNTICA À DO DESAFIO 3.
- Usuario: classe com dois herdeiros. IDÊNTICA À DO DESAFIO 3.
#### DTO
- EmpresaDto: classe reduzida da clase Empresa, usada para validar os dados obrigatórios que foram inseridos na API. IDÊNTICA À DO DESAFIO 3.
- PessoaDto: classe reduzida da clase Pessoa, usada para validar os dados obrigatórios que foram inseridos na API. IDÊNTICA À DO DESAFIO 3.

#### Repository
- EmpresaRepository: interfaz repositório que extende CrudRepository\<Empresa, Long\> para funções de busca em BD
  - Métodos principais OS MESMOS DO DESAFIO 3
- PessoaRepository OS MESMOS DO DESAFIO 3
#### Service
- EmpresaService: serviço que trabalha com o repositório EmpresaRepository e contém a lógica para armazenar o objeto. IDÊNTICO AO DO DESAFIO 3.
- PessoaService: serviço que trabalha com o repositório PessoaRepository e contém a lógica para armazenar o objeto. IDÊNTICO AO DO DESAFIO 3.
- SingledLinkedList: serviço que fornece métodos para fazer uma lista com objetos da classe Nodo. IDÊNTICO AO DO DESAFIO 3.
- SqsManualContainerInstantiation: serviço com métodos para enviar dados na fila SQS. IDÊNTICO AO DO DESAFIO 3.
- SecurityConfiguration: serviço com métodos para implementar segurança pedindo usuário e senha ao requerir dados das empresas e pessoas.
  - public SecurityFilterChain securityFilterChain(HttpSecurity http): Contrui um conjunto de regras para segurança, explicitando os métodos e *paths* liberados e restritos. Deviso ao uso de H2, o Swagger falha, e teve que se dar os acessos liberados para o Swagger e H2. Usando outro motor de BD evitaria o uso de exceçoes.
  - public InMemoryUserDetailsManager userDetailsService() Cria um usuário válido para ser usado e autenticar nos acessos às chamadas API restritas pela segurança implantada. Os valores de teste para este aplicativo foram
    - username: cielo
    - password: CIELO
    - roles: USUARIO
  - public WebSecurityCustomizer webSecurityCustomizer(): Adiciona exceçoes para evitar problemas com SecurityFilterChain e Swagger baseado em H2

#### Controller
- EmpresaController: Restcontroller que faz mapeamento das petições para /empresa.  IDÊNTICO AO DO DESAFIO 3.
- PessoaController: Restcontroller que faz mapeamento das petições para /pessoa.  IDÊNTICO AO DO DESAFIO 3.
- FilaController: Restcontroller que faz mapeamento das petições para /fila/aws.  IDÊNTICO AO DO DESAFIO 3.
#### Desafio4Application: Inicializador da aplicação
### To-Do
- Pode se adicionar mais camadas de segurança, como usar tokens. Mais tempo e requerimentos específicos permitiriam fazé-lo.
- Modificar os dados de username e senha para que sejam mais complexos (senha encriptada) e em BD para ter múltiplos usuários.
