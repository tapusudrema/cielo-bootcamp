package beingolea.org.desafio3;
import beingolea.org.desafio3.service.PessoaService;
import beingolea.org.desafio3.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafio3Application {
	private static PessoaService pessoaService;
	private static EmpresaService empresaService;


	@Autowired
	public Desafio3Application(PessoaService pessoaService,
							   EmpresaService empresaService
	) {
		this.pessoaService = pessoaService;
		this.empresaService = empresaService;
	}
	public static void main(String[] args) {
		SpringApplication.run(Desafio3Application.class, args);
	}

}
