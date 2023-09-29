package beingolea.org.desafio2;
import beingolea.org.desafio2.service.PessoaService;
import beingolea.org.desafio2.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafio2Application {
	private static PessoaService pessoaService;
	private static EmpresaService empresaService;


	@Autowired
	public Desafio2Application(PessoaService pessoaService,
							   EmpresaService empresaService
	) {
		this.pessoaService = pessoaService;
		this.empresaService = empresaService;
	}
	public static void main(String[] args) {
		SpringApplication.run(Desafio2Application.class, args);
	}

}
