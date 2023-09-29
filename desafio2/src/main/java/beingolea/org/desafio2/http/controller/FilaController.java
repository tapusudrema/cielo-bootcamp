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
    public String armarResultado(String tipo, String info) {
        String resultado = "";
        if(Objects.equals(tipo, "E")) {
            //buscar empresa
            Optional<Empresa> empresa = empresaService.buscarPorUuid(info);
            if (empresa.isPresent()) {
                resultado = empresa.get().toStringE();
            }
        } else {
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
        List<SingledLinkedList.Nodo> listado = Usuario.fila.listar();
        Usuario.fila.mostrar();

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
        if (resultado==null) {
            resultado = "Fila vazia!";
        } else {
            String tipo = resultado.substring(0,1);
            String info = resultado.substring(1);
            resultado = armarResultado(tipo, info);
        }
        return resultado;
    }
}