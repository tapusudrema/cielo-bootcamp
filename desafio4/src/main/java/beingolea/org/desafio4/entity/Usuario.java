package beingolea.org.desafio4.entity;
import beingolea.org.desafio4.service.SingledLinkedList;
import beingolea.org.desafio4.service.SqsManualContainerInstantiation;

public class Usuario {
    // Criaçao da fila de usuários (pessoas e empresas)
    public static SingledLinkedList fila = new SingledLinkedList();
    // Criaçao da instância AWS SQS
    public static SqsManualContainerInstantiation sqs;
}
