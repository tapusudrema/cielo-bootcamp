package beingolea.org.desafio2.dto;

import beingolea.org.desafio2.entity.Empresa;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class EmpresaDtoTest {
    @Test
    void EmpresaDtoTest() {
        EmpresaDto empresaDto =  new EmpresaDto();
        empresaDto.setCpf("12345678901");
        empresaDto.setEmail("e@mail.com");
        empresaDto.setMcc("1234");
        empresaDto.setNomecontato("Nome");
    }
    @Test
    void Empresa() {
        Empresa empresa = new Empresa("12345678901234",
                "Social","1234",
                "12345678901","Contato","e@mail.com");
    }
    @Test
    void toEmpresa() {
    }

    @Test
    void getCnpj() {
    }

    @Test
    void getRazaosocial() {
    }

    @Test
    void getMcc() {
    }

    @Test
    void getCpf() {
    }

    @Test
    void getNomecontato() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void setCnpj() {
    }

    @Test
    void setRazaosocial() {
    }

    @Test
    void setMcc() {
    }

    @Test
    void setCpf() {
    }

    @Test
    void setNomecontato() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void testEquals() {
    }
}