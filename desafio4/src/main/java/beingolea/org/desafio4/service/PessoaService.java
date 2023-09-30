package beingolea.org.desafio4.service;

import beingolea.org.desafio4.dto.PessoaDto;
import beingolea.org.desafio4.entity.Pessoa;
import beingolea.org.desafio4.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {

    //@Autowired
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa salvar(Pessoa pessoa){
        Optional<Pessoa> optionalPessoa = pessoaRepository.findByCpf(pessoa.getCpf());
        if (optionalPessoa.isPresent()) {
            if (optionalPessoa.get().getCpf() == null) {
                // nao existe pessoa com o CPF, inserir:
                return pessoaRepository.save(pessoa);
            } else {
                // pessoa existe, nao se insere no BD
                System.out.println("Pessoa repetida");
                return null;
            }
        } else {
            return pessoaRepository.save(pessoa);
            }
    }

    public Pessoa atualizar(PessoaDto pessoaDto, String uuid){
        Optional<Pessoa> optionalPessoa = pessoaRepository.findByUuid(uuid);
        if (optionalPessoa.isPresent()) {
            String cpf = pessoaDto.getCpf();
            // pegar os últimos 11 carateres:
            cpf = ("00000000000"+cpf).substring(cpf.length());
            if (Objects.equals(optionalPessoa.get().getCpf(), cpf)) {
                // coincidem uuid & cnpj: atualizar dados e salvar
                optionalPessoa.get().setEmail(pessoaDto.getEmail());
                optionalPessoa.get().setMcc(pessoaDto.getMcc());
                optionalPessoa.get().setNome(pessoaDto.getNome());
                return pessoaRepository.save(optionalPessoa.get());
            } else {
                // nao coincidem
                System.out.println("diferente cpf");
                return null;
            }
        } else {
            // nao existe o registro por UUID no BD
            System.out.println("nao existe uuid");
            return null;
        }
    }

    public Iterable<Pessoa> listaPessoa(){
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorUuid(String uuid){
        return pessoaRepository.findByUuid(uuid);
    }

    public void removerPorUuid(String uuid){
        pessoaRepository.deleteByUuid(uuid);
    }
}
