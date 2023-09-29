package beingolea.org.desafio2.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    //Represent the head and tail of the singly linked list
    public Nodo cabeca = null;
    public Nodo cola = null;

    //addNode() will add a new node to the list
    public void enfileirar(String info, String tipo) {
        //Create a new node
        Nodo nodoNovo = new Nodo(info, tipo);

        //Checks if the list is empty
        if (cabeca == null) {
            //Se a lista está vazia, cabeca e cola apontam ao novo nodo
            cabeca = nodoNovo;
        } else {
            cola.seguinte = nodoNovo;
        }
        cola = nodoNovo;
    }
    public String retirarCabeca() {
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
        //Node current will point to head
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