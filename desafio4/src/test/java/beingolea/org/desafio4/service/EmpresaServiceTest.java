package beingolea.org.desafio4.service;

import beingolea.org.desafio4.dto.EmpresaDto;
import beingolea.org.desafio4.entity.Empresa;
import beingolea.org.desafio4.repository.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
@SpringBootTest
class EmpresaServiceTest {
    @Autowired
    public EmpresaRepository empresaRepository;
    @Autowired
    public EmpresaService empresaService;
    @Test
    void salvar() {
        Empresa empresa = new Empresa("12345678901234","Social","0000","12345678901","Contato","e@mail.com");
        Empresa empresa1 = new EmpresaService(empresaRepository).salvar(empresa);
    }

    @Test
    void atualizar() {
        EmpresaDto empresaDto = new EmpresaDto();
        Empresa empresa = new Empresa("12345678901234","Social","0000","12345678901","Contato","e@mail.com");
        Empresa empresa1 = new EmpresaService(empresaRepository).atualizar(empresaDto,"0");
    }

    @Test
    void listaEmpresa() {
        Iterable<Empresa> lista = new EmpresaService(empresaRepository).listaEmpresa();
    }

    @Test
    void buscarPorUuid() {
        Optional<Empresa> buscar = new EmpresaService(empresaRepository).buscarPorUuid("000");
    }

    @Test
    void removerPorUuid() {
        empresaService.removerPorUuid ("0000");
    }
}