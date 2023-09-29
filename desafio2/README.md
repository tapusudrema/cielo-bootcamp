# Desafio 2 do Bootcamp Cielo
## User Story: Fila de atendimento
a) incluir na API criada no desafio “1” uma nova operação que possibilite a retirada do próximo cliente da fila de atendimento e retorne os dados disponíveis
b) implementar na linguagem java uma estrutura de dados para uma fila utilizando apenas tipos de dados primitivos (sem utilizar classes java.util.*), onde seja possível acrescentar e retirar clientes na fila no modelo FIFO (First In, First Out).
c) contemplar as regras da história de usuário através da implementação da operação modelada no item “a”, utilizando a estrutura de fila criada no item “b”
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
  - Características do objeto e do seu respectivo campo no BD. AS MESMAS DO DESAFIO 1
  - Métodos principais. OS MESMOS DO DESAFIO 1
    
- Pessoa: classe final que extende a classe Usuario, definindo o objeto Pessoa. O objeto criado se armazenará no banco de dados H2 na tabela *pessoa*
  - Características do objeto e do seu respectivo campo no BD. AS MESMAS DO DESAFIO 1
  - Métodos principais. OS MESMOS DO DESAFIO 1
- Usuario: classe com dois herdeiros.
  - Características do objeto:
    - public static SingledLinkedList fila = new SingledLinkedList(). Cria um objeto comum que será a fila dos usuários
#### DTO
- EmpresaDto: classe reduzida da clase Empresa, usada para validar os dados obrigatórios que foram inseridos na API
  - Características do objeto AS MESMAS DO DESAFIO 1
  - Método principal O MESMO DO DESAFIO 1
- PessoaDto: classe reduzida da clase Pessoa, usada para validar os dados obrigatórios que foram inseridos na API
  - Características do objeto AS MESMAS DO DESAFIO 1
  - Método principal O MESMO DO DESAFIO 1
#### Repository
- EmpresaRepository: interfaz repositório que extende CrudRepository\<Empresa, Long\> para funções de busca em BD
  - Métodos principais OS MESMOS DO DESAFIO 1
- PessoaRepository OS MESMOS DO DESAFIO 1
#### Service
- EmpresaService: serviço que trabalha com o repositório EmpresaRepository e contém a lógica para armazenar o objeto.
  - Métodos principais OS MESMOS DO DESAFIO 1
- PessoaService: serviço que trabalha com o repositório PessoaRepository e contém a lógica para armazenar o objeto.
  - Métodos principais OS MESMOS DO DESAFIO 1
- SingledLinkedList: serviço que fornece métodos para fazer uma lista com objetos da classe Nodo
  - Características do objeto
    - public class Nodo (String info; String tipo;Nodo seguinte)
    - public Nodo(String info, String tipo) Construtor
    - public SingledLinkedList() {this.cabeca = null; this.cola = null;} Instanciador
    - public Nodo cabeca = null; Cabeca e cola da lista:
    - public Nodo cola = null; Cola da lista:
  - Métodos principais
    - public void enfileirar(String info, String tipo). Adiciona um novo nodo na lista ao final (PUSH)
    - public String retirarCabeca() Extrai o primeiro elemento da lista (POP)
    - public void atualizar(String info, String tipo) Percorre a lista para localizar um nodo e atualizar
    - public void mostrar() Percorre a lista devolvendo como String um conjunto de linhas de texto onde cada representa o texto do nodo
    - public List\<Nodo\> listar() Convertir a lista em tipo List para retornar um iterável. Usado apenas com finalidad de *display*
#### Controller
- EmpresaController: Restcontroller que faz mapeamento das petições para /empresa. Os métodos usados são:
  - public ResponseEntity\<Empresa\> salvar. Recebe via POST dados como objeto validado EmpresaDto. Se a validação do objeto falha, retorna mensagem de erro de inserção (BAD REQUEST). Se no momento de salvar a empresa já existe no BD, retorna mensagem de erro de inserção (BAD REQUEST). Caso contrário, consigue armazenar no BD e retorna um HttpStatus CREATED *e coloca no final da fila*.
  - public Iterable\<Empresa\> listaEmpresa. Recebe via GET o requerimento e retorna o conjunto de empresas no BD com HttpStatus OK em formato JSON.
  - public Empresa buscarPorUuid. Recebe via método GET o requerimento com parámetro querystring uuid, retorna a empresa no BD com HttpStatus OK em formato JSON.
  - public Empresa removerEmpresa. Recebe via método DELETE o requerimento com parámetro querystring uuid, e executa a eliminação da empresa no BD associada ao uuid, com HttpStatus NO_CONTENT.
  - public ResponseEntity\<String\> atualizarEmpresa. Recebe via método PUT o requerimento com parámetro querystring uuid e dados como objeto EmpresaDto validado, e executa a atualização da empresa no BD associada ao uuid. Se a validação do objeto falha, retorna mensagem de erro de atualização por estrutura não apropriada (BAD REQUEST). Se não existe a empresa ou tenta modificar a chave CNPJ retorna mensagem de erro de atualização (BAD REQUEST). Se os dados batem, se atualiza o BD e retorna uma mensagem de succeso com HttpStatus NO_CONTENT *e coloca no final da fila*.
- PessoaController: Restcontroller que faz mapeamento das petições para /pessoa. Os métodos usados são:
  - public ResponseEntity\<Pessoa\> salvar. Recebe via POST dados como objeto validado PessoaDto. Se a validação do objeto falha, retorna mensagem de erro de inserção (BAD REQUEST). Se no momento de salvar a pessoa já existe no BD, retorna mensagem de erro de inserção (BAD REQUEST). Caso contrário, consigue armazenar no BD e retorna um HttpStatus CREATED *e coloca no final da fila*.
  - public Iterable\<Pessoa\> listaPessoa. Recebe via GET o requerimento e retorna o conjunto de pessoas no BD com HttpStatus OK em formato JSON.
  - public Pessoa buscarPessoaPorId. Recebe via método GET o requerimento com parámetro querystring uuid, retorna a pessoa no BD com HttpStatus OK em formato JSON.
  - public Pessoa removerPessoa. Recebe via método DELETE o requerimento com parámetro querystring uuid, e executa a eliminação da pessoa no BD associada ao uuid, com HttpStatus NO_CONTENT.
  - public ResponseEntity\<String\> atualizarPessoa. Recebe via método PUT o requerimento com parámetro querystring uuid e dados como objeto PessoaDto validado, e executa a atualização da pessoa no BD associada ao uuid. Se a validação do objeto falha, retorna mensagem de erro de atualização por estrutura não apropriada (BAD REQUEST). Se não existe a pessoa ou tenta modificar a chave CPF retorna mensagem de erro de atualização (BAD REQUEST). Se os dados batem, se atualiza o BD e retorna uma mensagem de succeso com HttpStatus NO_CONTENT *e coloca no final da fila*.
- FilaController: Restcontroller que faz mapeamento das petições para /fila. Os métodos usados são:
- public String armarResultado(String tipo, String info) {
        String resultado = "";
        if(Objects.equals(tipo, "E")) { //Empresa?
            //buscar empresa
            Optional<Empresa> empresa = empresaService.buscarPorUuid(info);
            if (empresa.isPresent()) {
                resultado = empresa.get().toStringE();
            }
        } else { //Pessoa
            //buscar pessoa
            Optional<Pessoa> pessoa = pessoaService.buscarPorUuid(info);
            if (pessoa.isPresent()) {
                resultado = pessoa.get().toStringC();
            }
        }
        return resultado;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String listaFila(){
        String resultado = "";
        // converter a um objeto iterável a fila
        List<SingledLinkedList.Nodo> listado = Usuario.fila.listar();
        Usuario.fila.mostrar();

        // construir um texto com todos os objetos da fila

        for (SingledLinkedList.Nodo nodox : listado) {
            String tipo = nodox.getTipo();
            String info = nodox.getInfo();
            resultado += armarResultado(tipo, info);
        }
        return (resultado.isEmpty() ?"Fila vazia!":resultado);
    }

    @GetMapping("/avancar")
    @ResponseStatus(HttpStatus.OK)
    public String avancarFila(){
        String resultado = "";
        resultado = Usuario.fila.retirarCabeca();
        // retorna o conteudo do topo da fila, e retira ele dela
        if (resultado==null) {
            resultado = "Fila vazia!";
        } else {
            String tipo = resultado.substring(0,1);
            String info = resultado.substring(1);
            resultado = armarResultado(tipo, info);
        }
        // retorna um string com os dados do usuario do topo da fila
        return resultado;
    }
}
- 
#### Desafio1Application: Inicializador da aplicação
### To-Do
- Validar se o CNPJ atende as normas brasileiras de contruçao de tal cadastro
- Validar se o CPF atende as normas brasileiras de contruçao de tal cadastro
- Otimizar o uso de métodos comuns
- Utilizar um banco de dados mais poderoso, e tentar usar comandos SQL, para passar a carga de BD para fora da aplicação Java
- Aperfeiçoar os testes unitários

