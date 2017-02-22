package br.com.mkacunha.execucao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import br.com.mkacunha.persistencia.EclipseLinkPersistencia;
import br.com.mkacunha.persistencia.HibernatePersistencia;
import br.com.mkacunha.persistencia.Persistencia;

public class ExecucaoTeste {

	public static final String LOG_EXECUCAO = "benchmark";
	public static final int QUANTIDADE_EXECUCOES_TESTE = 1;
	public static final int QUANTIDA_REGISTROS_BASE_TESTE = 100000;

	private Persistencia persistencia;
	private List<ResultadoExecucaoTeste> resultados = new ArrayList<>();
	private Logger logger;

	public ExecucaoTeste(Persistencia persistencia) {
		this.persistencia = persistencia;
		BasicConfigurator.configure();

		logger = Logger.getLogger(LOG_EXECUCAO);
		logger.setLevel(Level.ALL);

		Logger loggerOrg = Logger.getLogger("org");
		loggerOrg.setLevel(Level.OFF);

		Logger loggerAnteros = Logger.getLogger("br.com.anteros");
		loggerAnteros.setLevel(Level.OFF);
	}

	public void executar() {
		LocalDateTime dataInicio = LocalDateTime.now();

		//limparBaseDados();

		new ExecucaoPais(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoEstado(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoCidade(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoAtor(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoCategoria(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoIdioma(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoLocadora(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoFuncionario(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoCliente(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoFilme(this, QUANTIDADE_EXECUCOES_TESTE).executar();
		new ExecucaoAluguel(this, QUANTIDADE_EXECUCOES_TESTE).executar();

		logger.info("Fim execução de todos os testes");

		LocalDateTime dataFim = LocalDateTime.now();

		System.out.println();
		System.out.println();
		System.out.println("Data de início: " + dataInicio);
		System.out.println("Data de fim: " + dataFim);
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

	public ResultadoExecucaoTeste add(String operacao, Class<?> clazz, int quantidade) {
		StringBuilder sb = new StringBuilder();
		sb.append(operacao);
		sb.append(" por ");
		sb.append(quantidade);
		sb.append(" vez(es) objetos referentes a classe ");
		sb.append(clazz.getSimpleName());

		return add(sb.toString());
	}

	public ResultadoExecucaoTeste add(String descricao) {
		ResultadoExecucaoTeste resultadoExecucao = new ResultadoExecucaoTeste(descricao);
		resultados.add(resultadoExecucao);
		return resultadoExecucao;
	}

	public Persistencia getPersistencia() {
		return persistencia;
	}

	public void imprimirResultado() {
		resultados.forEach(r -> System.out.println(r));

	}

	public static void main(String[] args) {
		boolean isHibernate = false;

		ExecucaoTeste execucaoTeste = null;

		if (isHibernate)
			execucaoTeste = new ExecucaoTeste(new HibernatePersistencia());
		else
			execucaoTeste = new ExecucaoTeste(new EclipseLinkPersistencia());

		execucaoTeste.executar();
		execucaoTeste.imprimirResultado();
	}
}
