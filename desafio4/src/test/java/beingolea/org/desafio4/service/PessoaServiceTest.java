package beingolea.org.desafio4.service;

import beingolea.org.desafio4.dto.PessoaDto;
import beingolea.org.desafio4.entity.Pessoa;
import beingolea.org.desafio4.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
@SpringBootTest
class PessoaServiceTest {
    @Autowired
    public PessoaRepository pessoaRepository;
    @Autowired
    public PessoaService pessoaService;
    @Test
    void salvar() {
        Pessoa pessoa = new Pessoa("12345678901","0000","Nome","e@mail.com");
        Pessoa pessoa1 = new PessoaService(pessoaRepository).salvar(pessoa);
    }

    @Test
    void atualizar() {
        PessoaDto pessoaDto = new PessoaDto();
        Pessoa pessoa = new Pessoa("12345678901","0000","Nome","e@mail.com");
        Pessoa pessoa1 = new PessoaService(pessoaRepository).atualizar(pessoaDto,"0");
    }

    @Test
    void listaPessoa() {
        Iterable<Pessoa> lista = new PessoaService(pessoaRepository).listaPessoa();
    }

    @Test
    void buscarPorUuid() {
        Optional<Pessoa> buscar = new PessoaService(pessoaRepository).buscarPorUuid("000");
    }

    @Test
    void removerPorUuid() {
        pessoaService.removerPorUuid ("0000");
    }
}