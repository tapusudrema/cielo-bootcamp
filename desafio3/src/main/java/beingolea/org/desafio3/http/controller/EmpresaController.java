package beingolea.org.desafio3.http.controller;

import beingolea.org.desafio3.dto.EmpresaDto;
import beingolea.org.desafio3.entity.Empresa;
import beingolea.org.desafio3.service.EmpresaService;
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
@RequestMapping("/empresa")
public class EmpresaController {
        private EmpresaService empresaService;
        private SqsManualContainerInstantiation sqs;
        @Autowired
        public EmpresaController(EmpresaService empresaService, SqsManualContainerInstantiation sqsManualContainerInstantiation) {
            this.empresaService = empresaService;
            this.sqs = sqsManualContainerInstantiation;
        }

        @PostMapping
        //@ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<Empresa> salvar(@Valid @RequestBody EmpresaDto empresaDto, Errors errors) throws ExecutionException, InterruptedException {
            if (errors.hasErrors()) {
                // erros de validacao?
                System.out.println("Erro no esquema");
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Error-Insert", "Os dados nao tem a estrutura correta");
                return  ResponseEntity.badRequest()
                        .headers(responseHeaders)
                        .body(null);
            } else {
                Empresa createdEmpresa = empresaService.salvar(empresaDto.toEmpresa());
                if (createdEmpresa == null) {
                    // existe?
                        System.out.println("Nulo");
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.set("Error-Insert", "CNPJ já existe. Nao foi inserida a empresa, nem modificada");
                        return ResponseEntity.badRequest()
                                .headers(responseHeaders)
                                .body(null);
                    } else {
                        // a empresa foi criada no BD e vai ser enviada ao SQS:
                        //Usuario.fila.enfileirar(createdEmpresa.getUuid(),"E");
                        sqs.EnviaSQS(sqs.sqsAsyncClient, createdEmpresa.getUuid(),"E",true);
                        return new ResponseEntity<>(createdEmpresa, HttpStatus.CREATED);
                    }
                }
        }

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public Iterable<Empresa> listaEmpresa(){
            return empresaService.listaEmpresa();
        }

        @GetMapping("/{uuid}")
        @ResponseStatus(HttpStatus.OK)
        public Empresa buscarPorUuid(@PathVariable("uuid") String uuid){
            return empresaService.buscarPorUuid(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa nao encontrada."));
        }

        @DeleteMapping("/{uuid}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void removerEmpresa(@PathVariable("uuid") String uuid){
            Optional<Empresa> empresaOpt = empresaService.buscarPorUuid(uuid);
            empresaOpt.ifPresent(empresa -> empresaService.removerPorUuid(empresa.getUuid()));
        }

        @PutMapping("/{uuid}")
        //@ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<String> atualizarEmpresa(@Valid @RequestBody EmpresaDto empresaDto, Errors errors, @PathVariable("uuid") String uuid) throws ExecutionException, InterruptedException {
            if (errors.hasErrors()) {
                // erros de validacao?
                HttpHeaders responseHeaders = new HttpHeaders();
                System.out.println("Erro no esquema");
                responseHeaders.set("Error-Update", "Os dados nao tem a estrutura correta");
                return  ResponseEntity.badRequest()
                        .headers(responseHeaders)
                        .body(null);
            } else {
                HttpHeaders responseHeaders = new HttpHeaders();
                Empresa createdEmpresa = empresaService.atualizar(empresaDto, uuid);
                if (createdEmpresa == null) {
                    //existe ou pode ser atualizado?
                    responseHeaders.set("Error-Update", "Nao existe uuid ou já existe CNPJ");
                    return  ResponseEntity.badRequest()
                            .headers(responseHeaders)
                            .body(null);
                } else {
                // a EMPRESA foi atualizada no BD e vai ser enviada ao SQS:
                /*
                    FUNÇAO A IMPLEMENTAR, POIS SQS NAO PERMITE MOVER UM OBJETO DA FILA
                    TERIA QUE SER PERCORRIDA, LOCALIZADA, ELIMINADA, E ENVIADA NO FINAL
                    DA FILA, MAS O PERCORRIDO DA FILA DÁ PROBLEMAS PELA DISTRIBUIÇAO
                    EM VÁRIOS SERVIDORES NO MUNDO DOS ELEMENTOS DELA.
                    VAMOS INSERIR, NA TEORIA SQS NAO PERMITE A DUPLICIDADE DE ELEMENTOS
                    ENTAO CONFIAR QUE O ELEMENTO NOVO SENDO DUPLICADO, ELIMINE O ANTERIOR
                 */
                    sqs.EnviaSQS(sqs.sqsAsyncClient, createdEmpresa.getUuid(),"E",true);
                    //Usuario.fila.atualizar(createdEmpresa.getUuid(),"E");
                    return new ResponseEntity<>("Atualizado com sucesso",HttpStatus.NO_CONTENT);
                }
            }
        }
    }