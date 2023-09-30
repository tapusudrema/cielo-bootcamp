package beingolea.org.desafio3.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class PessoaTest {
    @Test
    void getUuid() {
        Pessoa pessoa = new Pessoa();
        String result = pessoa.getUuid();
        assertEquals(result.length(),36,"Conforme");
    }

    @Test
    void getNome() {
        Pessoa pessoa = new Pessoa();
        String result = pessoa.getNome();
        assertEquals(result,null,"Conforme");
    }
    @Test
    void getEmail() {
        Pessoa pessoa = new Pessoa();
        String result = pessoa.getEmail();
        assertEquals(result,null,"Conforme");
    }
    @Test
    void getMcc() {
        Pessoa pessoa = new Pessoa();
        String result = pessoa.getMcc();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void getCpf() {
        Pessoa pessoa = new Pessoa();
        String result = pessoa.getCpf();
        assertEquals(result,null,"Conforme");
    }
    @Test
    void setCpf() {
        Pessoa pessoa = new Pessoa();
        String cpf = "12345678901";
        pessoa.setCpf(cpf);
        assertEquals(pessoa.getCpf(),cpf,"Conforme");
    }
    @Test
    void setNome() {
        Pessoa pessoa = new Pessoa();
        String nome = "Nome";
        pessoa.setNome(nome);
        assertEquals(pessoa.getNome(),nome,"Conforme");
    }
    @Test
    void setMcc() {
        Pessoa pessoa = new Pessoa();
        String mcc = "1234";
        pessoa.setMcc(mcc);
        assertEquals(pessoa.getMcc(),mcc,"Conforme");
    }
    @Test
    void setEmail() {
        Pessoa pessoa = new Pessoa();
        String email = "e@mail.com";
        pessoa.setEmail(email);
        assertEquals(pessoa.getEmail(),email,"Conforme");
    }
    @Test
    void setDataCadastro(){
        Pessoa pessoa = new Pessoa ();
        LocalDateTime data = LocalDateTime.now();
        pessoa.setDataCadastro(data);
        assertEquals(pessoa.getDataCadastro().getYear(),data.getYear(),"conforme");
    }
    @Test
    void toStringP() {
        Pessoa pessoa = new Pessoa("12345678901","0000","Nome","e@mail.com");
        String texto = pessoa.toStringC();
        assertEquals(texto.isEmpty(),false,"conforme");

    }
    @Test
    void Pessoa() {
        Pessoa pessoa = new Pessoa("12345678901","0000","Nome","e@mail.com");
        LocalDateTime hora = pessoa.getDataCadastro();
        assertEquals(hora.getYear(),LocalDateTime.now().getYear(),"conforme");
    }
}