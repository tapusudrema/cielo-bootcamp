package beingolea.org.desafio4.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.sqs.endpoints.internal.Value;

import java.time.LocalDateTime;

@SpringBootTest
class EmpresaTest {

    @Test
    void getUuid() {
        Empresa empresa = new Empresa();
        String result = empresa.getUuid();
        assertEquals(result.length(),36,"Conforme");
    }

    @Test
    void getNomecontato() {
        Empresa empresa = new Empresa();
        String result = empresa.getNomecontato();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void setRazaosocial() {
        Empresa empresa = new Empresa();
        String razaosocial = "Social";
        empresa.setRazaosocial(razaosocial);
        assertEquals(empresa.getRazaosocial(),razaosocial,"Conforme");
    }

    @Test
    void setNomecontato() {
        Empresa empresa = new Empresa();
        String nomecontato = "Nome";
        empresa.setNomecontato(nomecontato);
        assertEquals(empresa.getNomecontato(),nomecontato,"Conforme");
    }
    @Test
    void setDataCadastro(){
        Empresa empresa = new Empresa();
        LocalDateTime data = LocalDateTime.now();
        empresa.setDataCadastro(data);
        assertEquals(empresa.getDataCadastro().getYear(),data.getYear(),"conforme");
    }

    @Test
    void getEmail() {
        Empresa empresa = new Empresa();
        String result = empresa.getEmail();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void setEmail() {
        Empresa empresa = new Empresa();
        String email = "e@mail.com";
        empresa.setEmail(email);
        assertEquals(empresa.getEmail(),email,"Conforme");
    }

    @Test
    void setCpf() {
        Empresa empresa = new Empresa();
        String cpf = "12345678901";
        empresa.setCpf(cpf);
        assertEquals(empresa.getCpf(),cpf,"Conforme");
    }

    @Test
    void setCnpj() {
        Empresa empresa = new Empresa();
        String cnpj = "12345678901234";
        empresa.setCnpj(cnpj);
        assertEquals(empresa.getCnpj(),cnpj,"Conforme");
    }

    @Test
    void setMcc() {
        Empresa empresa = new Empresa();
        String mcc = "1234";
        empresa.setMcc(mcc);
        assertEquals(empresa.getMcc(),mcc,"Conforme");
    }

    @Test
    void toStringE() {
        Empresa empresa = new Empresa("12345678901234","Social","0000","12345678901","Contato","e@mail.com");
        String texto = empresa.toStringE();
        assertEquals(texto.isEmpty(),false,"conforme");

    }

    @Test
    void getCnpj() {
        Empresa empresa = new Empresa();
        String result = empresa.getCnpj();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void getRazaosocial() {
        Empresa empresa = new Empresa();
        String result = empresa.getRazaosocial();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void getMcc() {
        Empresa empresa = new Empresa();
        String result = empresa.getMcc();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void getCpf() {
        Empresa empresa = new Empresa();
        String result = empresa.getCpf();
        assertEquals(result,null,"Conforme");
    }

    @Test
    void Empresa() {
        Empresa empresa = new Empresa("12345678901234","Social","0000","12345678901","Contato","e@mail.com");
        LocalDateTime hora = empresa.getDataCadastro();
        assertEquals(hora.getYear(),LocalDateTime.now().getYear(),"conforme");
    }
}