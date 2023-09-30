package beingolea.org.desafio2.dto;

import beingolea.org.desafio2.entity.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class PessoaDtoTest {
    @Test
    void PessoaDtoTest() {
        PessoaDto pessoaDto =  new PessoaDto();
        pessoaDto.setCpf("12345678901");
        pessoaDto.setEmail("e@mail.com");
        pessoaDto.setMcc("1234");
        pessoaDto.setNome("Nome");
    }
    @Test
    void Pessoa() {
        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setCpf("12345678901");
        pessoaDto.setEmail("e@mail.com");
        pessoaDto.setMcc("1234");
        pessoaDto.setNome("Nome");
        Pessoa pessoa = pessoaDto.toPessoa();
    }
    @Test
    void toPessoa() {
    }

    @Test
    void getMcc() {
    }

    @Test
    void getCpf() {
    }

    @Test
    void getNome() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void setMcc() {
    }

    @Test
    void setCpf() {
    }

    @Test
    void setNome() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}