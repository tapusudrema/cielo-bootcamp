# Desafio 1 do Bootcamp Cielo
## User Story: Pré-cadastro de clientes
a) modelar uma API REST com operações que possibilitem a criação, alteração, exclusão e consulta de pré-cadastros de clientes. O entregável deverá ser um documento swagger.
b) implementar na linguagem java utilizando o framework spring boot as APIs modeladas no item 1. Os dados podem ser armazenados em memória.
c) Implementar cobertura de 70% de testes unitários
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
### Classes e interfazes
#### Entity
- Empresa: classe final que extende a classe Usuario, definindo o objeto Empresa. O objeto criado se armazenará no banco de dados H2 na tabela *empresa*
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
    
- Pessoa: classe final que extende a classe Usuario, definindo o objeto Pessoa. O objeto criado se armazenará no banco de dados H2 na tabela *pessoa*
  - Características do objeto e do seu respectivo campo no BD
    - id inteiro autogerado em BD
    - uuid String, em BD: length = 36, nullable = false, name = "uuid". O UUID será usado em combinaçao com o CPF como chave par única, UUID tem maior complexidade que o ID autogerado, dando mais segurança.
    - cpf String, em BD length = 11, nullable = false, name = "cpf". CPF da pessoa formatado com zeros à esquerda totalizando 11 dígitos. Usado na lógica como chave. Não é aplicado um algoritmo de validação.
    - mcc String, em BD length = 4, nullable = false, name = "mcc". MCC - “Merchant Category Code“ tem até 4 dígitos.
    - nome String, em BD length = 50, nullable = false, name = "nome". Nome da pessoa usuária, texto de até 50 caracteres.
    - email String, em BD length = 100, nullable = false, name = "email". Email do contato do estabelecimento, texto de até 100 caracteres. A validação requerida usa uma expressão regular para validação:
     "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\.]+)\\.([a-zA-Z]{2,5})$") mas os relatos dos usuários indicaram defeitos. A regex utilizada se define na DTO
    - dataCadastro LocalDateTime, em BD nullable = false, name="datacadastro". Data do cadastro gerado com a data e hora do sistema.
  - Métodos principais
    - private String gerarUuid() retorna um objeto UUID aleatório convertido em String
    - public Pessoa é um construtor do objeto, asignando um valor 0 ao id, uuid do método anterior, data do relógio do sistema, e os campos procedentes do DTO no uso
    - public String toStringC() retorna os campos do objeto como uma linha de texto
- Usuario: classe com dois herdeiros.
#### DTO
- EmpresaDto: classe reduzida da clase Empresa, usada para validar os dados obrigatórios que foram inseridos na API
  - Características do objeto e de suas respectivas validações e RegEx. Tomam os tipos e limites dos campos análogos da classe que reduz.
    - cnpj @NotBlank(message = "CNPJ de 14 números obrigatório.") @Size(max = 14) @Pattern(regexp = "(\\d{1,14})", message = "o CNPJ nao é válido.")
    - razaosocial @NotBlank(message = "Razao social obrigatória.")
    - mcc @NotBlank(message = "MCC obrigatório.") @Pattern(regexp = "\\b([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])\\b")
    - cpf @NotBlank(message = "CPF do contato obrigatório.") @Size(max = 11) @Pattern(regexp = "(\\d{1,11})", message = "o CNPJ nao é válido.")
    - nomecontato @NotBlank(message = "Nome do contato obrigatório.")
    - email @NotBlank(message = "Email obrigatório.") @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$", flags = {Flag.CASE_INSENSITIVE, Flag.MULTILINE}, message = "o email nao é válido.")
  - Método principal
    - public toEmpresa() retorna um objeto do tipo Empresa passando os campos depois da validação (cnpj, razaosocial, mcc, cpf, nomecontato, email)
- PessoaDto: classe reduzida da clase Pessoa, usada para validar os dados obrigatórios que foram inseridos na API
  - Características do objeto e de suas respectivas validações e RegEx. Tomam os tipos e limites dos campos análogos da classe que reduz.
    - mcc @NotBlank(message = "MCC obrigatório.") @Pattern(regexp = "\\b([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])\\b")
    - cpf @NotBlank(message = "CPF da pessoa obrigatório.") @Size(max = 11) @Pattern(regexp = "(\\d{1,11})", message = "o CPF nao é válido.")
    - nome @NotBlank(message = "Nome da pessoa obrigatório.")
    - email @NotBlank(message = "Email obrigatório.") @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$", flags = {Flag.CASE_INSENSITIVE, Flag.MULTILINE}, message = "o email nao é válido.")
  - Método principal
    - public toPessoa() retorna um objeto do tipo Pessoa passando os campos depois da validação (cpf, mcc, nome, email)
#### Repository
- EmpresaRepository: interfaz repositório que extende CrudRepository\<Empresa, Long\> para funções de busca em BD
  - Métodos principais
    - findByUuid(String uuid) retorna um recordset segundo a coincidencia do parámetro uuid com o campo uuid na BD
    - findByCpf(String cpf); retorna um recordset segundo a coincidencia do parámetro cpf com o campo cpf na BD
    - findByCnpj(String cnpj) retorna um recordset segundo a coincidencia do parámetro cnpj com o campo cnpj na BD
    - deleteByUuid(String uuid) elimina um registro segundo a coincidencia do parámetro uuid com o campo uuid na BD
- PessoaRepository
    - findByUuid(String uuid) retorna um recordset segundo a coincidencia do parámetro uuid com o campo uuid na BD
    - findByCpf(String cpf); retorna um recordset segundo a coincidencia do parámetro cpf com o campo cpf na BD
    - findByCnpj(String cnpj) retorna um recordset segundo a coincidencia do parámetro cnpj com o campo cnpj na BD
    - deleteByUuid(String uuid) elimina um registro segundo a coincidencia do parámetro uuid com o campo uuid na BD
#### Service
- EmpresaService: serviço que trabalha com o repositório EmpresaRepository e contém a lógica para armazenar o objeto.
  - Métodos principais
    - public Empresa salvar: A partir do objeto Empresa passado, o método procura no BD se já existe com o CNPJ fornecido. Se não existe, guarda o objeto. Caso contrário, manda uma mensagem de empresa repetida.
    - public Empresa atualizar(EmpresaDto empresaDto, String uuid). A partir do objeto EmpresaDto validado, o método procura no BD se já existe com o UUID fornecido. Se não existe, retorna uma mensagem de empresa não existente. Se existe o UUID, se consulta a coincidência dos CNPJ para continuar com a atualização no BD do registro. Se o CNPJ é diferente daquele que se tenta atualizar retorna uma mensagem de CNPJ diferentes.
    - public Iterable\<Empresa\> listaEmpresa(). Retorna um recordset das empresas armazenadas no BD.
    - public Optional\<Empresa\> buscarPorUuid(). Retorna um recordset da empresa armazenada no BD que coincida com o parámetro UUID de entrada.
    - public void removerPorUuid(). Elimina o recordset da empresa armazenada no BD que coincida com o parámetro UUID de entrada.
- PessoaService: serviço que trabalha com o repositório PessoaRepository e contém a lógica para armazenar o objeto.
  - Métodos principais
    - public Pessoa salvar: A partir do objeto Pessoa passado, o método procura no BD se já existe com o CPF fornecido. Se não existe, guarda o objeto. Caso contrário, manda uma mensagem de pessoa repetida.
    - public Pessoa atualizar(PessoaDto pessoaDto, String uuid). A partir do objeto PessoaDto validado, o método procura no BD se já existe com o UUID fornecido. Se não existe, retorna uma mensagem de pessoa/uuid não existente.
      Se existe o UUID, se consulta a coincidência dos CPF para continuar com a atualização no BD do registro. Se o CPF é diferente daquele que se tenta atualizar retorna uma mensagem de CPF diferentes.
    - public Iterable\<Pessoa\> listaPessoa(). Retorna um recordset das pessoas armazenadas no BD.
    - public Optional\<Pessoa\> buscarPorUuid(). Retorna um recordset da pessoa armazenada no BD que coincida com o parámetro UUID de entrada.
    - public void removerPorUuid(). Elimina o recordset da pessoa armazenada no BD que coincida com o parámetro UUID de entrada.
#### Controller
- EmpresaController: Restcontroller que faz mapeamento das petições para /empresa. Os métodos usados são:
  - public ResponseEntity\<Empresa\> salvar. Recebe via POST dados como objeto validado EmpresaDto. Se a validação do objeto falha, retorna mensagem de erro de inserção (BAD REQUEST). Se no momento de salvar a empresa já existe no BD, retorna mensagem de erro de inserção (BAD REQUEST). Caso contrário, consigue armazenar no BD e retorna um HttpStatus CREATED
  - public Iterable\<Empresa\> listaEmpresa. Recebe via GET o requerimento e retorna o conjunto de empresas no BD com HttpStatus OK em formato JSON.
  - public Empresa buscarPorUuid. Recebe via método GET o requerimento com parámetro querystring uuid, retorna a empresa no BD com HttpStatus OK em formato JSON.
  - public Empresa removerEmpresa. Recebe via método DELETE o requerimento com parámetro querystring uuid, e executa a eliminação da empresa no BD associada ao uuid, com HttpStatus NO_CONTENT.
  - public ResponseEntity\<String\> atualizarEmpresa. Recebe via método PUT o requerimento com parámetro querystring uuid e dados como objeto EmpresaDto validado, e executa a atualização da empresa no BD associada ao uuid. Se a validação do objeto falha, retorna mensagem de erro de atualização por estrutura não apropriada (BAD REQUEST). Se não existe a empresa ou tenta modificar a chave CNPJ retorna mensagem de erro de atualização (BAD REQUEST). Se os dados batem, se atualiza o BD e retorna uma mensagem de succeso com HttpStatus NO_CONTENT.
- PessoaController: Restcontroller que faz mapeamento das petições para /pessoa. Os métodos usados são:
  - public ResponseEntity\<Pessoa\> salvar. Recebe via POST dados como objeto validado PessoaDto. Se a validação do objeto falha, retorna mensagem de erro de inserção (BAD REQUEST). Se no momento de salvar a pessoa já existe no BD, retorna mensagem de erro de inserção (BAD REQUEST). Caso contrário, consigue armazenar no BD e retorna um HttpStatus CREATED
  - public Iterable\<Pessoa\> listaPessoa. Recebe via GET o requerimento e retorna o conjunto de pessoas no BD com HttpStatus OK em formato JSON.
  - public Pessoa buscarPessoaPorId. Recebe via método GET o requerimento com parámetro querystring uuid, retorna a pessoa no BD com HttpStatus OK em formato JSON.
  - public Pessoa removerPessoa. Recebe via método DELETE o requerimento com parámetro querystring uuid, e executa a eliminação da pessoa no BD associada ao uuid, com HttpStatus NO_CONTENT.
  - public ResponseEntity\<String\> atualizarPessoa. Recebe via método PUT o requerimento com parámetro querystring uuid e dados como objeto PessoaDto validado, e executa a atualização da pessoa no BD associada ao uuid. Se a validação do objeto falha, retorna mensagem de erro de atualização por estrutura não apropriada (BAD REQUEST). Se não existe a pessoa ou tenta modificar a chave CPF retorna mensagem de erro de atualização (BAD REQUEST). Se os dados batem, se atualiza o BD e retorna uma mensagem de succeso com HttpStatus NO_CONTENT.
#### Desafio1Application: Inicializador da aplicação
### To-Do
- Validar se o CNPJ atende as normas brasileiras de contruçao de tal cadastro
- Validar se o CPF atende as normas brasileiras de contruçao de tal cadastro
- Otimizar o uso de métodos comuns
- Utilizar um banco de dados mais poderoso, e tentar usar comandos SQL, para passar a carga de BD para fora da aplicação Java
- Aperfeiçoar os testes unitários
