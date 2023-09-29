package beingolea.org.desafio3.entity;
import java.util.UUID;

public class MensagemSQS {
    private UUID id;
    private String name;
    public MensagemSQS() {}
    public MensagemSQS(UUID id, String name){
        this.id = id;
        this.name = name;
    }
    public UUID getId() {
        return id;
    }
    public void setUuid(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
