package beingolea.org.desafio4.http.controller;
import io.awspring.cloud.sqs.operations.SqsTemplateBuilder;
import beingolea.org.desafio4.entity.Empresa;
import beingolea.org.desafio4.entity.Pessoa;
import beingolea.org.desafio4.entity.Usuario;
import beingolea.org.desafio4.service.EmpresaService;
import beingolea.org.desafio4.service.PessoaService;
import beingolea.org.desafio4.service.SingledLinkedList;
import beingolea.org.desafio4.service.SqsManualContainerInstantiation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sqs.model.Message;


import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import java.util.List;
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
        List<SingledLinkedList.Nodo> listado = Usuario.fila.listar();
        Usuario.fila.mostrar();

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
        if (resultado==null) {
            resultado = "Fila vazia!";
        } else {
            String tipo = resultado.substring(0,1);
            String info = resultado.substring(1);
            resultado = armarResultado(tipo, info);
        }
        return resultado;
    }

    @GetMapping("/aws")
    @ResponseStatus(HttpStatus.OK)
    public String filaAWS() throws ExecutionException, InterruptedException {

        String resultado = "";
        System.out.println("Entrando.....");
        resultado = Usuario.fila.retirarCabeca();
        if (resultado==null) {
            resultado = "Fila vazia!.";
        } else {
            String tipo = resultado.substring(0,1);
            String info = resultado.substring(1);
            resultado = armarResultado(tipo, info);
            sqsManualContainerInstantiation.EnviaSQS(sqsManualContainerInstantiation.sqsAsyncClient, info,tipo, true);
            //algo.EnviaSQS(sqsManualContainerInstantiation.sqsAsyncClient, info,tipo, true);
        }
        //Collection<org.springframework.messaging.Message<?>> mensagens = algo.ListSQS(sqsManualContainerInstantiation.sqsAsyncClient);
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
    public String enfileirarAWS() throws ExecutionException, InterruptedException {
        String resultado = "";
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
