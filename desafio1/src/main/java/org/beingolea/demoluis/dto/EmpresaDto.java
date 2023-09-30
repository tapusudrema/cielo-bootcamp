package org.beingolea.demoluis.dto;
import org.beingolea.demoluis.entity.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.Data;

@Data
public class EmpresaDto {
    @NotBlank(message = "CNPJ de 14 números obrigatório.")
    @Size(max = 14)
    @Pattern(regexp = "(\\d{1,14})", message = "o CNPJ nao é válido.")
    private String cnpj;

    @NotBlank(message = "Razao social obrigatória.")
    private String razaosocial;
    @NotBlank(message = "MCC obrigatório.")
    @Pattern(regexp = "\\b([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])\\b")
    private String mcc;
    @NotBlank(message = "CPF do contato obrigatório.")
    @Size(max = 11)
    @Pattern(regexp = "(\\d{1,11})", message = "o CNPJ nao é válido.")
    private String cpf;
    @NotBlank(message = "Nome do contato obrigatório.")
    private String nomecontato;

    @NotBlank(message = "Email obrigatório.")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$", flags = {Flag.CASE_INSENSITIVE, Flag.MULTILINE}, message = "o email nao é válido.")
    private String email;

    public Empresa toEmpresa() {
        return new Empresa(cnpj, razaosocial, mcc, cpf, nomecontato, email);
    }
}