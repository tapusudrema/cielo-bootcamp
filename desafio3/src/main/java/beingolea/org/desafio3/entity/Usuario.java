package beingolea.org.desafio3.entity;
import beingolea.org.desafio3.service.SingledLinkedList;
import beingolea.org.desafio3.service.SqsManualContainerInstantiation;

public class Usuario {
    public static SingledLinkedList fila = new SingledLinkedList();
    public static SqsManualContainerInstantiation sqs;
    //public Usuario(SqsManualContainerInstantiation sqsManualContainerInstantiation) {
    //    sqs = sqsManualContainerInstantiation;
    //}
    //sqsManualContainerInstantiation.EnviaSQS(sqsManualContainerInstantiation.sqsAsyncClient, info,tipo, true);
}
