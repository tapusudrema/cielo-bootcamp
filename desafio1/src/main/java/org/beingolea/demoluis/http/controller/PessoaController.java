package org.beingolea.demoluis.http.controller;
import jakarta.validation.Valid;
import org.beingolea.demoluis.dto.PessoaDto;
import org.beingolea.demoluis.entity.Pessoa;
import org.beingolea.demoluis.service.PessoaService;
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

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
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
                responseHeaders.set("Error-Insert", "CPF já existe. Nao foi inserida a pessoa");
                return ResponseEntity.badRequest()
                        .headers(responseHeaders)
                        .body(null);
            } else {
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
    }

    @PutMapping("/{uuid}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> atualizarPessoa(@Valid @RequestBody PessoaDto pessoaDto, Errors errors, @PathVariable("uuid") String uuid){
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
                responseHeaders.set("Error-Update", "CPF já existe ou nao existe uuid. Nao foi atualizada a pessoa");
                return ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body(null);
            } else {
                return new ResponseEntity<>("Atualizado com sucesso", HttpStatus.CREATED);
            }
        }
    }
}