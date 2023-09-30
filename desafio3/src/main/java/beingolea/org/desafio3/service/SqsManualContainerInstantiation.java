package beingolea.org.desafio3.service;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
//import software.amazon.awssdk.services.sqs.;

@Configuration
public class SqsManualContainerInstantiation {
    public static final String FILA_SQS_AWS = "mi.fifo";//miColita
    private static final Logger LOGGER = LoggerFactory.getLogger(SqsManualContainerInstantiation.class);

    @Bean
    public SqsTemplate sqsTemplateManualContainerInstantiation(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

    public record TipoNodo(String tipo, String uuid) {
    }

    public final SqsAsyncClient sqsAsyncClient;
    public SqsManualContainerInstantiation(SqsAsyncClient sqsAsyncClient) {
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public SendResult<Object> EnviaSQS(SqsAsyncClient sqsAsyncClient2,
                                       String uuid, String tipo, Boolean envia) throws ExecutionException, InterruptedException {
        if (envia) {
            SqsTemplate sqsTemplate = sqsTemplateManualContainerInstantiation(sqsAsyncClient2);
            LOGGER.info("Enviando mensagem");
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .build();
            return sqsTemplate.send(to -> to.queue(FILA_SQS_AWS)
                    .payload(new TipoNodo(tipo, uuid)));
        } else {return null;}
    }
    /*
    public Collection<Message<?>> ListSQS(SqsAsyncClient sqsAsyncClient2)  {
        SqsTemplate sqsTemplate = sqsTemplateManualContainerInstantiation(sqsAsyncClient2);
        //// Receive a batch of messages with the provided options and convert the payloads to the provided class.
        //<T> Collection<Message<T>> receiveMany(Consumer<SqsReceiveOptions> from, Class<T> payloadClass);
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .visibilityTimeout(1)
                .build();
        Collection<Message<?>> messages = sqsTemplate.receiveMany(from -> from.queue(FILA_SQS_AWS));
        return messages;
    }
    */

    public List<software.amazon.awssdk.services.sqs.model.Message> ListSQS2(SqsAsyncClient sqsAsyncClient2) throws ExecutionException, InterruptedException {
        SqsTemplate sqsTemplate = sqsTemplateManualContainerInstantiation(sqsAsyncClient2);
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .visibilityTimeout(10)
                .build();
        List<software.amazon.awssdk.services.sqs.model.Message> mensagens = sqsAsyncClient2
                .receiveMessage(receiveMessageRequest).get().messages();
        return mensagens;
    }



    public Optional<org.springframework.messaging.Message<?>> ElementoSQS(SqsAsyncClient sqsAsyncClient2)  {
        SqsTemplate sqsTemplate = sqsTemplateManualContainerInstantiation(sqsAsyncClient2);
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .visibilityTimeout(1)
                .build();
        return sqsTemplate.receive(from -> from.queue(FILA_SQS_AWS));
   }
}