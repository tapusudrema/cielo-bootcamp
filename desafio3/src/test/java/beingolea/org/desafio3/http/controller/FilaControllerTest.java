package beingolea.org.desafio3.http.controller;

import beingolea.org.desafio3.repository.EmpresaRepository;
import beingolea.org.desafio3.repository.PessoaRepository;
import beingolea.org.desafio3.service.EmpresaService;
import beingolea.org.desafio3.service.PessoaService;
import beingolea.org.desafio3.service.SqsManualContainerInstantiation;
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
    public  SqsManualContainerInstantiation sqsManualContainerInstantiation;
    @MockBean
    private FilaController filaController;// = new FilaController(empresaService, pessoaService, sqsManualContainerInstantiation);

    @Test
    void FilaController() {
        FilaController fila = new FilaController(empresaService,
                pessoaService, sqsManualContainerInstantiation);
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

    @Test
    void filaAWS() throws ExecutionException, InterruptedException {
        String resultado = filaController.filaAWS();
        assertEquals(resultado,null,"Conforme");
    }

    @Test
    void enfileirarAWS() throws ExecutionException, InterruptedException {
        String resultado = filaController.avancarAWS();
        assertEquals(resultado,null,"Conforme");
    }
}