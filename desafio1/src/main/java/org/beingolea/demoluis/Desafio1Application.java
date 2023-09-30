package org.beingolea.demoluis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.beingolea.demoluis.service.*;


@SpringBootApplication
public class Desafio1Application {
	private static PessoaService pessoaService;
	private static EmpresaService empresaService;

	@Autowired
	public Desafio1Application(PessoaService pessoaService,
							   EmpresaService empresaService
							   ) {
		this.pessoaService = pessoaService;
		this.empresaService = empresaService;
	}
	public static void main(String[] args) {
		SpringApplication.run(Desafio1Application.class, args);
	}
}