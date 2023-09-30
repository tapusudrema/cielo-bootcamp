package beingolea.org.desafio2.http.controller;

import beingolea.org.desafio2.entity.Empresa;
import beingolea.org.desafio2.entity.Pessoa;
import beingolea.org.desafio2.entity.Usuario;
import beingolea.org.desafio2.service.EmpresaService;
import beingolea.org.desafio2.service.PessoaService;
import beingolea.org.desafio2.service.SingledLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/fila")
public class FilaController {
    private EmpresaService empresaService;
    private PessoaService pessoaService;
    @Autowired
    public FilaController(EmpresaService empresaService,
                          PessoaService pessoaService) {
        this.empresaService = empresaService;
        this.pessoaService = pessoaService;
    }

    // funçao que retorna um string com a representaçao em uma linha do objeto
    public String armarResultado(String tipo, String info) {
        String resultado = "";
        if(Objects.equals(tipo, "E")) { //Empresa?
            //buscar empresa
            Optional<Empresa> empresa = empresaService.buscarPorUuid(info);
            if (empresa.isPresent()) {
                resultado = empresa.get().toStringE();
            }
        } else { //Pessoa
            //buscar pessoa
            Optional<Pessoa> pessoa = pessoaService.buscarPorUuid(info);
            if (pessoa.isPresent()) {
                resultado = pessoa.get().toStringC();
            }
        }
        return resultado;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String listaFila(){
        String resultado = "";
        // converter a um objeto iterável a fila
        List<SingledLinkedList.Nodo> listado = Usuario.fila.listar();
        Usuario.fila.mostrar();

        // construir um texto com todos os objetos da fila

        for (SingledLinkedList.Nodo nodox : listado) {
            String tipo = nodox.getTipo();
            String info = nodox.getInfo();
            resultado += armarResultado(tipo, info);
        }
        return (resultado.isEmpty() ?"Fila vazia!":resultado);
    }

    @GetMapping("/avancar")
    @ResponseStatus(HttpStatus.OK)
    public String avancarFila(){
        String resultado = "";
        resultado = Usuario.fila.retirarCabeca();
        // retorna o conteudo do topo da fila, e retira ele dela
        if (resultado==null) {
            resultado = "Fila vazia!";
        } else {
            String tipo = resultado.substring(0,1);
            String info = resultado.substring(1);
            resultado = armarResultado(tipo, info);
        }
        // retorna um string com os dados do usuario do topo da fila, forçando sua eliminaçao em esta
        return resultado;
    }
}