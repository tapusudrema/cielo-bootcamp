package beingolea.org.desafio2.http.controller;

import beingolea.org.desafio2.repository.EmpresaRepository;
import beingolea.org.desafio2.repository.PessoaRepository;
import beingolea.org.desafio2.service.EmpresaService;
import beingolea.org.desafio2.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(FilaController.class)

class FilaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public EmpresaRepository empresaRepository;
    @MockBean
    public PessoaRepository pessoaRepository;
    @MockBean
    public EmpresaService empresaService;// = new EmpresaService(empresaRepository);
    @MockBean
    public PessoaService pessoaService;// = new PessoaService(pessoaRepository);

    @MockBean
    private FilaController filaController;// = new FilaController(empresaService, pessoaService, sqsManualContainerInstantiation);

    @Test
    void FilaController() {
        FilaController fila = new FilaController(empresaService, pessoaService);
        assertEquals(fila.armarResultado("a","b"),"","Conforme");
    }
    @Test
    void armarResultado() {
    }

    @Test
    void listaFila() {
        //this.mockMvc.perform()
        String texto = filaController.listaFila();
        assertEquals(texto,null,"Conforme");

    }

    @Test
    void avancarFila() throws ExecutionException, InterruptedException {
        String resultado = filaController.avancarFila();
        assertEquals(resultado,null,"Conforme");
    }
}