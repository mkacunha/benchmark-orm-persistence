package br.com.mkacunha.execucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.mkacunha.modelo.Aluguel;
import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.modelo.Categoria;
import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Cliente;
import br.com.mkacunha.modelo.Estado;
import br.com.mkacunha.modelo.Filme;
import br.com.mkacunha.modelo.Funcionario;
import br.com.mkacunha.modelo.Idioma;
import br.com.mkacunha.modelo.Locadora;
import br.com.mkacunha.modelo.Pais;
import br.com.mkacunha.operacao.ResultadoExecucao;
import br.com.mkacunha.operacao.ResultadoExecucaoOperacao;
import br.com.mkacunha.operacao.TipoOperacao;
import br.com.mkacunha.persistencia.EclipseLinkPersistencia;
import br.com.mkacunha.persistencia.HibernatePersistencia;
import br.com.mkacunha.persistencia.Persistencia;

public class ExecucaoTeste {

	public static final String LOG_EXECUCAO = "benchmark";
	public static final int QUANTIDA_REGISTROS_BASE_TESTE = 100000;

	private Persistencia persistencia;
	private ResultadoExecucao resultadoExecucao;
	private Logger logger;
	private String framework;
	private int numeroExecucao;

	public ExecucaoTeste(Persistencia persistencia, String framework, int numeroExecucao) {
		this.framework = framework;
		this.numeroExecucao = numeroExecucao;
		resultadoExecucao = new ResultadoExecucao();
		this.persistencia = persistencia;
		BasicConfigurator.configure();

		logger = Logger.getLogger(LOG_EXECUCAO);
		logger.setLevel(Level.ALL);

		Logger loggerOrg = Logger.getLogger("org");
		loggerOrg.setLevel(Level.OFF);
	}

	public void executar() {
		LocalDateTime dataInicio = LocalDateTime.now();

		//limparBaseDados();

		new ExecucaoPais(this).executar();
		new ExecucaoEstado(this).executar();
		new ExecucaoCidade(this).executar();
		new ExecucaoAtor(this).executar();
		new ExecucaoCategoria(this).executar();
		new ExecucaoIdioma(this).executar();
		new ExecucaoLocadora(this).executar();
		new ExecucaoFuncionario(this).executar();
		new ExecucaoCliente(this).executar();
		new ExecucaoFilme(this).executar();
		new ExecucaoAluguel(this).executar();

		logger.info("Fim execução de todos os testes");

		LocalDateTime dataFim = LocalDateTime.now();

		System.out.println();
		System.out.println();
		System.out.println("Data de início: " + dataInicio);
		System.out.println("Data de fim: " + dataFim);
		
		resultadoExecucao.imprimirResultado();
		resultadoExecucao.salvarArquivo(framework, numeroExecucao);
	}

	private void limparBaseDados() {
		logger.info("Iniciando limpeza da base dados");

		removeAll(Aluguel.class);
		removeAll(Filme.class);
		removeAll(Cliente.class);

		logger.info("Alterabdo de todos os registros referente a classe locadora o atributo gerente para null");
		List<Locadora> locadoras = persistencia.findAll(Locadora.class);
		locadoras.forEach(locadora -> locadora.setGerente(null));
		persistencia.save(locadoras);

		removeAll(Funcionario.class);
		removeAll(Locadora.class);
		removeAll(Categoria.class);
		removeAll(Idioma.class);
		removeAll(Ator.class);
		removeAll(Cliente.class);
		removeAll(Cidade.class);
		removeAll(Estado.class);
		removeAll(Pais.class);
	}

	private void removeAll(Class<?> clazz) {
		logger.debug("Removendo registros da referente a classe " + clazz.getSimpleName());
		persistencia.removeAll(clazz);
	}

	public ResultadoExecucaoOperacao add(Class<?> clazz, TipoOperacao operacao) {
		return resultadoExecucao.add(clazz, operacao);
	}

	public Persistencia getPersistencia() {
		return persistencia;
	}
	
	public String getFramework() {
		return framework;
	}
	
	public int getNumeroExecucao() {
		return numeroExecucao;
	}
}
