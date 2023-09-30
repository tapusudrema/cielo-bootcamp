package beingolea.org.desafio4.dto;
import beingolea.org.desafio4.entity.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.Data;

@Data
public class PessoaDto {
    @NotBlank(message = "MCC obrigatório.")
    @Pattern(regexp = "\\b([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])\\b")
    private String mcc;
    @NotBlank(message = "CPF da pessoa obrigatório.")
    @Size(max = 11)
    @Pattern(regexp = "(\\d{1,11})", message = "o CPF nao é válido.")
    private String cpf;
    @NotBlank(message = "Nome da pessoa obrigatório.")
    private String nome;

    @NotBlank(message = "Email obrigatório.")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$", flags = {Flag.CASE_INSENSITIVE, Flag.MULTILINE}, message = "o email nao é válido.")
    private String email;

    public Pessoa toPessoa() {
        return new Pessoa(cpf, mcc, nome, email);
    }
}
