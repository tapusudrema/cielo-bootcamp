package org.beingolea.demoluis.http.controller;
import jakarta.validation.Valid;
import org.beingolea.demoluis.dto.EmpresaDto;
import org.beingolea.demoluis.entity.Empresa;
import org.beingolea.demoluis.service.EmpresaService;
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

        @Autowired
        public EmpresaController(EmpresaService empresaService) {
            this.empresaService = empresaService;
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
        public ResponseEntity<String> atualizarEmpresa(@Valid @RequestBody EmpresaDto empresaDto, Errors errors, @PathVariable("uuid") String uuid){
            //HttpHeaders responseHeaders = new HttpHeaders();
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
                    return new ResponseEntity<>("Atualizado com sucesso",HttpStatus.NO_CONTENT);
                }
            }
        }
    }