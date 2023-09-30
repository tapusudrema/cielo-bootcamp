package beingolea.org.desafio2.http.controller;

import beingolea.org.desafio2.repository.EmpresaRepository;
import beingolea.org.desafio2.service.EmpresaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(EmpresaController.class)

public class EmpresaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    public EmpresaRepository empresaRepository;
    @MockBean
    public EmpresaService empresaService = new EmpresaService(empresaRepository);

    @MockBean
    private EmpresaController empresaController = new EmpresaController(empresaService);

    @Test
    public void listaEmpresa() throws Exception {
        Mockito.when(empresaController.listaEmpresa()).then(null);
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/empresa").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}