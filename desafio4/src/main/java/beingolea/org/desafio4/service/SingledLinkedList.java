package beingolea.org.desafio4.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Implementaçao de uma lista linear usando ponteiros de classe Nodo vinculados entre eles
public class SingledLinkedList {
    //Representacao de um nodo numa lista vinculada linearmente
    public class Nodo {
        String info;
        String tipo;
        Nodo seguinte;

        public Nodo(String info, String tipo) {
            this.info = info;
            this.tipo = tipo;
            this.seguinte = null;
        }
        public String getInfo() {return this.info;}
        public String getTipo() {return this.tipo;}
    }
    public SingledLinkedList() {this.cabeca = null; this.cola = null;}
    // Cabeca e cola da lista:
    public Nodo cabeca = null;
    public Nodo cola = null;

    // enfileirar vai adicionar um novo nodo na lista ao final (PUSH)
    public void enfileirar(String info, String tipo) {
        //Criar nodo
        Nodo nodoNovo = new Nodo(info, tipo);

        //Verifica se a lista está vazia
        if (cabeca == null) {
            //Se a lista está vazia, cabeca e cola apontam ao novo nodo
            cabeca = nodoNovo;
        } else {
            cola.seguinte = nodoNovo;
        }
        cola = nodoNovo;
    }
    public String retirarCabeca() {
        // extrai o primeiro elemento da lista (POP)
        if(cabeca == null) {
            return null;
        } else {
            String texto = cabeca.tipo + cabeca.info;
            if (cabeca.seguinte != null) {
                cabeca = cabeca.seguinte;
            } else {
                cabeca = null;
                cola = null;
            }
            return texto;
        }
    }
    public void atualizar(String info, String tipo) {
        // percorre a lista para localizar um nodo e atualizar
        Nodo puntero = cabeca;
        if (cabeca!=null && !(Objects.equals(cola.info, info) && Objects.equals(cola.tipo, tipo))) {
            if (Objects.equals(puntero.info, info) && Objects.equals(puntero.tipo, tipo)) {
                cabeca = puntero.seguinte;
                cola.seguinte = puntero;
                puntero.seguinte = null;
            } else {
                Nodo pivote = puntero.seguinte;
                while (pivote != null) {
                    if (Objects.equals(pivote.info, info) && Objects.equals(pivote.tipo, tipo)) {
                        puntero.seguinte = pivote.seguinte;
                        pivote.seguinte = null;
                        cola.seguinte = pivote;
                        pivote = null;
                    } else {
                        pivote = pivote.seguinte;
                        puntero = puntero.seguinte;
                    }
                }
            }
        }
    }
    public void mostrar() {
        // O nodo atual vai apontar para a cabeça
        Nodo atual = cabeca;

        if (cabeca == null) {
            System.out.println("Lista vazia!");
            return;
        }
        System.out.println("Nodos da lista: ");
        while (atual != null) {
            //imprime cada nodo consecutivo
            System.out.println("("+ atual.tipo + "," + atual.info + ") ");
            atual = atual.seguinte;
        }
    }
    public List<Nodo> listar() { //convertir a lista em tipo List para retornar um iterável
        List<Nodo> listado = new ArrayList<Nodo>();
        Nodo atual = cabeca;
        while (atual != null) {
            //itera cada nodo consecutivo
            listado.add(atual);
            atual = atual.seguinte;
        }
        return listado;
    }
}