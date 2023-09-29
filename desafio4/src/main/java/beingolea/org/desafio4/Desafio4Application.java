package beingolea.org.desafio4;
import beingolea.org.desafio4.service.PessoaService;
import beingolea.org.desafio4.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafio4Application {
	private static PessoaService pessoaService;
	private static EmpresaService empresaService;


	@Autowired
	public Desafio4Application(PessoaService pessoaService,
							   EmpresaService empresaService
	) {
		this.pessoaService = pessoaService;
		this.empresaService = empresaService;
	}
	public static void main(String[] args) {
		SpringApplication.run(Desafio4Application.class, args);
	}

}
