# Desafio 4 do Bootcamp Cielo [(derivado do Desafio 3)](../desafio3/README.md)
## Technical Debt: Segurança da Informação
a) identifique um débito técnico de Segurança da Informação na aplicação
b) detalhe o débito técnico identificado, informando a criticidade e possíveis consequências
c) planeje as atividades técnicas para o desenvolvimento da solução
d) implemente a solução
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
  - Métodos principais OS MESMOS DO DESAFIO 2
- PessoaRepository OS MESMOS DO DESAFIO 2
#### Service
- EmpresaService: serviço que trabalha com o repositório EmpresaRepository e contém a lógica para armazenar o objeto.
  - Métodos principais OS MESMOS DO DESAFIO 2
- PessoaService: serviço que trabalha com o repositório PessoaRepository e contém a lógica para armazenar o objeto.
  - Métodos principais OS MESMOS DO DESAFIO 2
- SingledLinkedList: serviço que fornece métodos para fazer uma lista com objetos da classe Nodo
  - Características do objeto O MESMOS DO DESAFIO 2
  - Métodos principais OS MESMOS DO DESAFIO 2
- SqsManualContainerInstantiation: serviço com métodos para enviar dados na fila SQS
  - public static final String FILA_SQS_AWS = "mi.fifo"; SQS do tipo FIFO em AWS
  - private static final Logger LOGGER = LoggerFactory.getLogger(SqsManualContainerInstantiation.class)
  - public SqsTemplate sqsTemplateManualContainerInstantiation(SqsAsyncClient sqsAsyncClient) Instanciador
  - public record TipoNodo(String tipo, String uuid) Tipo de nodo para colocar na fila, é um objeto com 2 características String, uma o tipo de usuário (E para empresa, P para pessoa) e o UUID do usuário
  - public final SqsAsyncClient sqsAsyncClient Cliente assíncrono SQS
  - public EnviaSQS() Manda os dados para ser colocados na fila AWS. É o método que realmente faz o PUSH
  - public ListSQS2(SqsAsyncClient sqsAsyncClient2) Método que busca obter a lista de mensagens no SQS
  - public Optionalz\<Message\<?\>\> ElementoSQS(SqsAsyncClient sqsAsyncClient2) Método que extrai o primeiro elemento da fila SQS (PUSH)
  - # O modelo de AWS com as filas SQS é um sistema repartido em vários servidores no mundo. Tentar obter a lista gerava dois caminhos dependendo do método. Um conseguia a lista, mas apagava dos servidores, era como fazer um POP de todos os elementos. O segundo método conseguia alguns poucos, aleatoriamente, dependendo do servidor do mundo. Nenhum dos dois foi útil para tentar ser usado para localizar um nodo de um usuário atualizado e eliminar com um código Handle que traz, e fazer PUSH do elemeno.Optou se por fazer outra vez o PUSH do elemento atualizado, considerando que AWS nao permite 2 mensagens iguais na mesma fila, e tentando ver a forma de que o novo PUSH elimine o duplicado ou mande ele no final. Pendente.

#### Controller
- EmpresaController: Restcontroller que faz mapeamento das petições para /empresa. Os métodos usados são:
  - public ResponseEntity\<Empresa\> salvar. Recebe via POST dados como objeto validado EmpresaDto. Se a validação do objeto falha, retorna mensagem de erro de inserção (BAD REQUEST). Se no momento de salvar a empresa já existe no BD, retorna mensagem de erro de inserção (BAD REQUEST). Caso contrário, consigue armazenar no BD e retorna um HttpStatus CREATED *e faz o PUSH para AWS SQS*.
  - public Iterable\<Empresa\> listaEmpresa. Recebe via GET o requerimento e retorna o conjunto de empresas no BD com HttpStatus OK em formato texto. Nao está funcionando devido ao problema de AWS SQS para obter a totalidade da fila.
  - public Empresa buscarPorUuid. Recebe via método GET o requerimento com parámetro querystring uuid, retorna a empresa no BD com HttpStatus OK em formato JSON. Nao está funcionando devido ao problema de AWS SQS para obter um elemento aleatório da fila.
  - public Empresa removerEmpresa. Recebe via método DELETE o requerimento com parámetro querystring uuid, e executa a eliminação da empresa no BD associada ao uuid, com HttpStatus NO_CONTENT. Nao está funcionando devido ao problema de AWS SQS para obter um elemento aleatório da fila
  - public ResponseEntity\<String\> atualizarEmpresa. Recebe via método PUT o requerimento com parámetro querystring uuid e dados como objeto EmpresaDto validado, e executa a atualização da empresa no BD associada ao uuid. Se a validação do objeto falha, retorna mensagem de erro de atualização por estrutura não apropriada (BAD REQUEST). Se não existe a empresa ou tenta modificar a chave CNPJ retorna mensagem de erro de atualização (BAD REQUEST). Se os dados batem, se atualiza o BD e retorna uma mensagem de succeso com HttpStatus NO_CONTENT *e faz o PUSH para AWS SQS*.
- PessoaController: Restcontroller que faz mapeamento das petições para /pessoa. Os métodos usados são:
  - public ResponseEntity\<Pessoa\> salvar. Recebe via POST dados como objeto validado PessoaDto. Se a validação do objeto falha, retorna mensagem de erro de inserção (BAD REQUEST). Se no momento de salvar a pessoa já existe no BD, retorna mensagem de erro de inserção (BAD REQUEST). Caso contrário, consigue armazenar no BD e retorna um HttpStatus CREATED *e faz o PUSH para AWS SQS*.
  - public Iterable\<Pessoa\> listaPessoa. Recebe via GET o requerimento e retorna o conjunto de pessoas no BD com HttpStatus OK em formato JSON. Nao está funcionando devido ao problema de AWS SQS para obter a totalidade da fila.
  - public Pessoa buscarPessoaPorId. Recebe via método GET o requerimento com parámetro querystring uuid, retorna a pessoa no BD com HttpStatus OK em formato JSON. Nao está funcionando devido ao problema de AWS SQS para obter um elemento aleatório da fila.
  - public Pessoa removerPessoa. Recebe via método DELETE o requerimento com parámetro querystring uuid, e executa a eliminação da pessoa no BD associada ao uuid, com HttpStatus NO_CONTENT. Nao está funcionando devido ao problema de AWS SQS para obter um elemento aleatório da fila
  - public ResponseEntity\<String\> atualizarPessoa. Recebe via método PUT o requerimento com parámetro querystring uuid e dados como objeto PessoaDto validado, e executa a atualização da pessoa no BD associada ao uuid. Se a validação do objeto falha, retorna mensagem de erro de atualização por estrutura não apropriada (BAD REQUEST). Se não existe a pessoa ou tenta modificar a chave CPF retorna mensagem de erro de atualização (BAD REQUEST). Se os dados batem, se atualiza o BD e retorna uma mensagem de succeso com HttpStatus NO_CONTENT *e faz o PUSH para AWS SQS*.
- FilaController: Restcontroller que faz mapeamento das petições para /fila/aws. Os métodos usados são os mesmos do desafio 2, e adiciona para /fila/aws:
  - public String filaAWS(): Recebe via método GET em /fila/aws o requerimento e retorna o conjunto de elementos da fila SQS. Sua funcionalide depende de Amazon, como comentado linhas acima; pelo tempo do desafio, faltou pesquisar mais.
  - public String avancarAWS(): Recebe via método GET em /fila/avancar e pega o primeiro elemento da fila SQS, eliminando ele dela, retornando com HttpStatus OK em formato String  @ResponseStatus(HttpStatus.OK)

#### Desafio1Application: Inicializador da aplicação
### To-Do
- Pesquisar mais com Amazon de como percorrer uma fila SQS para poder localizar um elemento e eliminar, para poder atualizar a fila com as condiçoes do desafio 2
- Depois de validar as pesquisas, retirar o código da lista simples enlazada SingledLinkedList herdada do desafio 2
- Validar se o CNPJ atende as normas brasileiras de contruçao de tal cadastro
- Validar se o CPF atende as normas brasileiras de contruçao de tal cadastro
- Otimizar o uso de métodos comuns
- Utilizar um banco de dados mais poderoso, e tentar usar comandos SQL, para passar a carga de BD para fora da aplicação Java, e dar persistência à informaçao
