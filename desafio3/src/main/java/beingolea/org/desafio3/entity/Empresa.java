package beingolea.org.desafio3.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Getter
@Entity
@Table(name = "empresa")

public final class Empresa extends Usuario {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 36, nullable = false, name = "uuid")
    private String uuid;
    @Column(length = 14, nullable = false, name = "cnpj")
    private String cnpj; //CNPJ formatado com zeros à esquerda
    @Column(length = 50, nullable = false, name = "razaosocial")
    private String razaosocial;//Razão Social
    @Column(length = 4, nullable = false, name = "mcc")
    private String mcc; //MCC - “Merchant Category Code“
    @Column(length = 11, nullable = false, name = "cpf") //CPF do contato formatado com zeros à esquerda
    private String cpf;
    @Column(length = 50, nullable = false, name = "nomecontato")
    private String nomecontato; //Nome do contato do estabelecimento
    @Column(length = 100, nullable = false, name = "email")
    private String email; //Email do contato do estabelecimento
    //expressão regular para validação: "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\.]+)\\.([a-zA-Z]{2,5})$")
    //^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[A-Z0-9-]+(?:\.[A-Z0-9-]+)*$
    //^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\.)+[A-Z]{2,6}$
    @Column(nullable = false, name="datacadastro")
    private LocalDateTime dataCadastro;
    private String gerarUuid(){
        return UUID.randomUUID().toString();
    }

    public Empresa(){
        this.uuid = gerarUuid();
    }

    public Empresa(String cnpj, String razaosocial, String mcc, String cpf,
                   String nomecontato, String email){
        this.id = 0L;
        this.uuid = gerarUuid();
        this.cnpj = ("00000000000000"+cnpj.trim()).substring(cnpj.trim().length());
        this.razaosocial = razaosocial.trim();
        this.mcc = mcc.trim();
        this.cpf = ("00000000000"+cpf.trim()).substring(cpf.trim().length());
        this.nomecontato = nomecontato.trim();
        this.email = email.trim();
        this.dataCadastro = LocalDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public String getNomecontato() {
        return nomecontato;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial.trim();
    }
    public void setNomecontato(String nomecontato) {this.nomecontato = nomecontato.trim();}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }
    public void setCpf(String cpf) { this.cpf = cpf.trim(); }
    public void setCnpj(String cnpj) { this.cnpj = cnpj.trim(); }
    public void setMcc(String mcc) { this.mcc = mcc.trim(); }

    public String toStringE() {
        return "Empresa {" +
                "uuid=" + uuid +
                ", razaosocial='" + razaosocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", mcc='" + mcc + '\'' +
                ", nomecontato='" + nomecontato + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", dataCadastro=" + dataCadastro.toString() +
                "}\n";
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
            this.dataCadastro = dataCadastro;
    }
}
