package beingolea.org.desafio2.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Getter
@Entity
@Table(name = "pessoa")
public final class Pessoa extends Usuario {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 36, nullable = false, name="uuid")
    private String uuid;
    @Column(length = 11, nullable = false, name="cpf") //CPF do contato formatado com zeros à esquerda
    private String cpf;
    @Column(length = 4, nullable = false, name = "mcc")
    private String mcc; //MCC - “Merchant Category Code“
    @Column(length = 50, nullable = false, name = "nome")
    private String nome;//nome da pessoa física
    @Column(length = 100, nullable = false, name = "email")
    private String email; //Email do contato do estabelecimento
    //expressão regular para validação: "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\.]+)\\.([a-zA-Z]{2,5})$")
    @Column(nullable = false, name="datacadastro")
    private LocalDateTime dataCadastro;

    public Pessoa(){
        this.uuid = gerarUuid();this.id = 0L;
    }
    /*
            Construtor do objeto Pessoa
            o campo UUID se completa com o gerador de objetos UUID, o CPF é
            concatenado pela esquerda com 11 zeros, e ficam os últimos 11
            dígitos tomando substring. Todos os campos recebem um tratamento
            trim para eliminar espaços em branco. A data é preenchida com
            o valor do relógio do sistema.
     */
    public Pessoa(String cpf, String mcc, String nome, String email){
        this.id = 0L;
        this.uuid = gerarUuid();
        this.cpf = ("00000000000"+cpf.trim()).substring(cpf.trim().length());
        this.mcc = mcc.trim();
        this.nome = nome.trim();
        this.email = email.trim();
        this.dataCadastro = LocalDateTime.now();
    }
    private String gerarUuid(){
        return UUID.randomUUID().toString();
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCpf() {return cpf;}
    public void setCpf(String cpf) { this.cpf = cpf.trim(); }
    public void setMcc(String mcc) { this.mcc = mcc.trim(); }
    public void setNome(String nome) {
        this.nome = nome.trim();
    }
    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String toStringC() {
        return "Pessoa{" +
                "uuid=" + uuid +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", mcc='" + mcc + '\'' +
                ", email='" + email + '\'' +
                ", dataCadastro=" + dataCadastro.toString() +
                "}\n";
    }
}