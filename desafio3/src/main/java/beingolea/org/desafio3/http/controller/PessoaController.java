package beingolea.org.desafio3.http.controller;

import beingolea.org.desafio3.dto.PessoaDto;
import beingolea.org.desafio3.entity.Pessoa;
import beingolea.org.desafio3.service.PessoaService;
import beingolea.org.desafio3.service.SqsManualContainerInstantiation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    //@Autowired
    private PessoaService pessoaService;
    private SqsManualContainerInstantiation sqs;

    @Autowired
    public PessoaController(PessoaService pessoaService,  SqsManualContainerInstantiation sqsManualContainerInstantiation) {
        this.pessoaService = pessoaService;
        this.sqs = sqsManualContainerInstantiation;
    }
    
    @PostMapping
    public ResponseEntity<Pessoa> salvar(@Valid @RequestBody PessoaDto pessoaDto, Errors errors) throws ExecutionException, InterruptedException {
        if (errors.hasErrors()) {
            // erros de validacao?
            System.out.println("Erro no esquema");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Error-Insert", "Os dados nao tem a estrutura correta");
            return ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body(null);
        } else {
            Pessoa createdPessoa = pessoaService.salvar(pessoaDto.toPessoa());
            if (createdPessoa == null) {
                // existe?
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Error-Insert", "CPF já existe. Nao foi inserido a pessoa");
                return ResponseEntity.badRequest()
                        .headers(responseHeaders)
                        .body(null);
            } else {
                //Usuario.fila.enfileirar(createdPessoa.getUuid(),"P");
                // a pessoa foi criada no BD e vai ser enviada ao SQS:
                sqs.EnviaSQS(sqs.sqsAsyncClient, createdPessoa.getUuid(),"P",true);
                return new ResponseEntity<>(createdPessoa, HttpStatus.CREATED);
            }
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Pessoa> listaPessoa(){
        return pessoaService.listaPessoa();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Pessoa buscarPessoaPorId(@PathVariable("uuid") String uuid){
        return pessoaService.buscarPorUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa nao encontrada."));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPessoa(@PathVariable("uuid") String uuid){
        Optional<Pessoa> pessoaOpt = pessoaService.buscarPorUuid(uuid);
        pessoaOpt.ifPresent(pessoa -> pessoaService.removerPorUuid(pessoa.getUuid()));

//                .map( (Cliente) cliente -> {
//                    clienteService.removerPorId(cliente);
//                    return Void.TYPE;
//                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa nao encontrada."));
    }

    @PutMapping("/{uuid}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> atualizarPessoa(@Valid @RequestBody PessoaDto pessoaDto, Errors errors, @PathVariable("uuid") String uuid) throws ExecutionException, InterruptedException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (errors.hasErrors()) {
            // erros de validacao?
            System.out.println("Erro no esquema");
            responseHeaders.set("Error-Update", "Os dados nao tem a estrutura correta");
            return  ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body(null);
        } else {
            Pessoa createdPessoa = pessoaService.atualizar(pessoaDto, uuid);
            if (createdPessoa == null) {
                // existe ou pode ser atualizado?
                System.out.println("nao existe uuid ou tenta modificar cpf");
                responseHeaders.set("Error-Update", "CPF já existe ou nao existe uuid. Nao foi atualizado o cliente");
                return ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body(null);
            } else {
                // a pessoa foi atualizada no BD e vai ser enviada ao SQS:
                /*
                    FUNÇAO A IMPLEMENTAR, POIS SQS NAO PERMITE MOVER UM OBJETO DA FILA
                    TERIA QUE SER PERCORRIDA, LOCALIZADA, ELIMINADA, E ENVIADA NO FINAL
                    DA FILA, MAS O PERCORRIDO DA FILA DÁ PROBLEMAS PELA DISTRIBUIÇAO
                    EM VÁRIOS SERVIDORES NO MUNDO DOS ELEMENTOS DELA.
                    VAMOS INSERIR, NA TEORIA SQS NAO PERMITE A DUPLICIDADE DE ELEMENTOS
                    ENTAO CONFIAR QUE O ELEMENTO NOVO SENDO DUPLICADO, ELIMINE O ANTERIOR
                 */
                sqs.EnviaSQS(sqs.sqsAsyncClient, createdPessoa.getUuid(),"P",true);
                //Usuario.fila.atualizar(createdPessoa.getUuid(),"P");
                return new ResponseEntity<>("Atualizado com sucesso", HttpStatus.CREATED);
            }
        }
    }
}