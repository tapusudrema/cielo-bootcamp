package beingolea.org.desafio4.repository;

import beingolea.org.desafio4.entity.Pessoa;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
    Optional<Pessoa> findByUuid(String uuid);
    Optional<Pessoa> findByCpf(String cpf);
    void deleteByUuid(String uuid);
}
