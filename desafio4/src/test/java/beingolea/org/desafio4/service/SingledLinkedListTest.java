package beingolea.org.desafio4.service;

import beingolea.org.desafio4.service.SingledLinkedList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SingledLinkedListTest {

    @Test
    void enfileirar() {
        String tipo = "C";
        String info="123456789";
        SingledLinkedList fila;
        fila = new SingledLinkedList();
        fila.enfileirar(info,tipo);
    }
    @Test
    void retirarCabeca() {
        String tipo = "C";
        String info="123456789";
        SingledLinkedList fila;
        fila = new SingledLinkedList();
        fila.enfileirar(info,tipo);
        String resultado = fila.retirarCabeca();
        System.out.println(resultado);
    }
    @Test
    void mostrar() {
        SingledLinkedList fila;
        fila = new SingledLinkedList();
        String tipo = "C";
        String info="123456789";
        fila.mostrar();
        fila.enfileirar(info,tipo);
        fila.enfileirar(info,tipo);
        fila.mostrar();
    }
    @Test
    void atualizar() {
        SingledLinkedList fila;
        fila = new SingledLinkedList();
        fila.enfileirar("000000000","C");
        fila.enfileirar("000000000","D");
        fila.mostrar();
        //fila.atualizar("123456789","C");
        fila.atualizar("000000000","C");
        fila.mostrar();
    }
}