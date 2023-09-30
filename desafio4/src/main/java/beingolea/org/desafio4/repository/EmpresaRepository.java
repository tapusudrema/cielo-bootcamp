package beingolea.org.desafio4.repository;

import beingolea.org.desafio4.entity.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmpresaRepository  extends CrudRepository<Empresa, Long>{
    Optional<Empresa> findByUuid(String uuid);
    Optional<Empresa> findByCpf(String cpf);
    Optional<Empresa> findByCnpj(String cnpj);
    void deleteByUuid(String uuid);
}