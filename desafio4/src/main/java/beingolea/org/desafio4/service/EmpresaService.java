package beingolea.org.desafio4.service;

import beingolea.org.desafio4.dto.EmpresaDto;
import beingolea.org.desafio4.entity.Empresa;
import beingolea.org.desafio4.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa salvar(Empresa empresa){
        Optional<Empresa> optionalEmpresa = empresaRepository.findByCnpj(empresa.getCnpj());
        if (optionalEmpresa.isPresent()) {
            if (optionalEmpresa.get().getCnpj() == null) {
                return empresaRepository.save(empresa);
            } else {
                System.out.println("repetida");
                return null;
            }
        } else {
            return empresaRepository.save(empresa);
        }
    }
    public Empresa atualizar(EmpresaDto empresaDto, String uuid) {
        Optional<Empresa> optionalEmpresa = empresaRepository.findByUuid(uuid);
        if (optionalEmpresa.isPresent()) {
            String cnpj = empresaDto.getCnpj();
            cnpj = ("00000000000000"+cnpj).substring(cnpj.length());
            if (Objects.equals(optionalEmpresa.get().getCnpj(), cnpj)) { //coincidem uuid & cnpj
                optionalEmpresa.get().setEmail(empresaDto.getEmail());
                optionalEmpresa.get().setMcc(empresaDto.getMcc());
                optionalEmpresa.get().setNomecontato(empresaDto.getNomecontato());
                return empresaRepository.save(optionalEmpresa.get());
            } else {
                System.out.println("diferente cnpj");
                return null;
            }
        } else {
            System.out.println("nao existe uuid");
            return null;
        }
    }

    public Iterable<Empresa> listaEmpresa(){
        return empresaRepository.findAll();
    }

    public Optional<Empresa> buscarPorUuid(String uuid){
        return empresaRepository.findByUuid(uuid);
    }

    public void removerPorUuid(String uuid){
        empresaRepository.deleteByUuid(uuid);
    }
}
