package beingolea.org.desafio3.http.controller;

import beingolea.org.desafio3.entity.Empresa;
import beingolea.org.desafio3.entity.Pessoa;
import beingolea.org.desafio3.entity.Usuario;
import beingolea.org.desafio3.service.EmpresaService;
import beingolea.org.desafio3.service.PessoaService;
import beingolea.org.desafio3.service.SingledLinkedList;
import beingolea.org.desafio3.service.SqsManualContainerInstantiation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/fila")
public class FilaController {
    private EmpresaService empresaService;
    private PessoaService pessoaService;
    private SqsManualContainerInstantiation sqsManualContainerInstantiation;
    public static final String FILA_SQS_AWS = "miColita";
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsManualContainerInstantiation.class);
    @Autowired
    public FilaController(EmpresaService empresaService,
                          PessoaService pessoaService,
                          SqsManualContainerInstantiation sqsManualContainerInstantiation) {
        this.empresaService = empresaService;
        this.pessoaService = pessoaService;
        this.sqsManualContainerInstantiation = sqsManualContainerInstantiation;
    }
    // funçao que retorna um string com a representaçao em uma linha do objeto
    public String armarResultado(String tipo, String info) {
        String resultado = "";
        if(Objects.equals(tipo, "E")) {
            //buscar empresa
            Optional<Empresa> empresa = empresaService.buscarPorUuid(info);
            if (empresa.isPresent()) {
                resultado = empresa.get().toStringE();
            }
        } else {
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

    @GetMapping("/aws")
    @ResponseStatus(HttpStatus.OK)
    public String filaAWS() throws ExecutionException, InterruptedException {
                /*
                    FUNÇAO A IMPLEMENTAR, POIS SQS NAO PERMITE LISTAR A TOTALIDADE REAL
                    DOS OBJETOS DA FILA PELA DISTRIBUIÇAO MUNDIAL DOS ELEMENTOS DA LISTA
                    EM VÁRIOS SERVIDORES.
                    VAMOS INSERIR, NA TEORIA SQS NAO PERMITE A DUPLICIDADE DE ELEMENTOS
                    ENTAO CONFIAR QUE O ELEMENTO NOVO SENDO DUPLICADO, ELIMINE O ANTERIOR
                 */
        String resultado = "";
        System.out.println("Entrando.....");
        // O SEGUINTE MÉTODO (COMENTADO) PERMITE TRAZER OS ELEMENTOS DA FILA, MAS TAMBÉM ELIMINA, DEIXANDO VAZI
        //Collection<org.springframework.messaging.Message<?>> mensagens = algo.ListSQS(sqsManualContainerInstantiation.sqsAsyncClient);

        // O SEGUINTE MÉTODO TRAZE ELEMENTOS DA FILA, MAS NAO TODOS, PELA FORMA DISTRIBUIDA NO MUNDO DA FILA
        List<software.amazon.awssdk.services.sqs.model.Message> mensagens = sqsManualContainerInstantiation.ListSQS2(sqsManualContainerInstantiation.sqsAsyncClient);
        System.out.print("Listado de Mensagens: ");
        System.out.println(mensagens.size());
        for(Message msg:mensagens) {
        //for(org.springframework.messaging.Message<?> msg:mensagens) {
            System.out.println(msg.body()+msg.receiptHandle());
            //System.out.println(msg.getPayload().toString());
        }

        return resultado;
    }
    @GetMapping("/aws/avancar")
    @ResponseStatus(HttpStatus.OK)
    public String avancarAWS() throws ExecutionException, InterruptedException {
        String resultado = "";
        // PEGA O ELEMENTO DA FILA AWS E ELIMINA DA FILA
        Optional<org.springframework.messaging.Message<?>> mensagem = sqsManualContainerInstantiation.ElementoSQS(sqsManualContainerInstantiation.sqsAsyncClient);

        if (mensagem==null) {
            resultado = "Fila vazia!!";
        } else {
            String cont = mensagem.toString();
            if (cont.length() > 20) {
                String paylo = mensagem.get().getPayload().toString();
                String tipo = paylo.substring(14,15);
                String info = paylo.substring(22,58);
                resultado = armarResultado(tipo, info);
            } else {
                resultado = "Erro. Reintentar";
            }
        }
        return resultado;
    }
}
