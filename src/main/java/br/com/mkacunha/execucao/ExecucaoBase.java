package br.com.mkacunha.execucao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.mkacunha.persistencia.Persistencia;

public abstract class ExecucaoBase<T> {

	private static final String OPERACAO_PERSISTIR = "Persistir";
	private static final String OPERACAO_RECUPERAR = "Recuperar";
	private static final String OPERACAO_ALTERAR = "Alterar";
	private static final String OPERACAO_REMOVER = "Remover";

	private Class<?> clazz;
	private int quantidade;
	protected ExecucaoTeste execucao;
	protected Persistencia persistencia;
	protected Logger logger;
	protected FileWriter file;

	public ExecucaoBase(ExecucaoTeste execucao, int quantidade) {
		this.execucao = execucao;
		this.quantidade = quantidade;
		this.persistencia = execucao.getPersistencia();
		logger = Logger.getLogger(ExecucaoTeste.LOG_EXECUCAO);

		this.clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		try {
			file = new FileWriter("log.txt", true);
		} catch (IOException e) {
			logger.error("Erro ao abrir arquivo de log:" + e.getMessage());
		}
	}

	public abstract List<T> getObjetosPersistir();

	public abstract void alterarDadosObjetos(List<T> objetos);

	public abstract void fimExecucaoTeste();

	public abstract void executarAposOperacaoPersistencia();

	public void executar() {
		logger.info("Inicou execução de teste de operações referente a classe " + clazz.getSimpleName());

		ResultadoExecucaoTeste persistir = execucao.add(OPERACAO_PERSISTIR, clazz, quantidade);
		ResultadoExecucaoTeste recuperar = execucao.add(OPERACAO_RECUPERAR, clazz, quantidade);
		ResultadoExecucaoTeste alterar = execucao.add(OPERACAO_ALTERAR, clazz, quantidade);
		ResultadoExecucaoTeste remover = execucao.add(OPERACAO_REMOVER, clazz, quantidade);

		for (int i = 0; i < quantidade; i++) {
			logger.info("Iniciou execução de teste número " + (i + 1));
			executarOperacoes(persistir, recuperar, alterar, remover);
		}

		logger.info("Fim da execução de testes de operações, chamada do método fimExecucaoTeste()");
		fimExecucaoTeste();

		try {
			file.write(persistir.toString());
			file.write(recuperar.toString());
			file.write(alterar.toString());
			file.write(remover.toString());
			file.close();
		} catch (IOException e) {
			logger.error("Erro ao fechar arquivo de log: " + e.getMessage());
		}
	}

	protected void executarOperacoes(ResultadoExecucaoTeste persistir, ResultadoExecucaoTeste recuperar,
			ResultadoExecucaoTeste alterar, ResultadoExecucaoTeste remover) {
		executarOperacaoPersistir(persistir);
		executarAposOperacaoPersistencia();
		List<T> objetos = executarOperacaoRecuperar(recuperar);
		executarOperacaoAlterar(alterar, objetos);
		executarOperacaoRemover(remover, objetos);
	}

	protected void executarOperacaoPersistir(ResultadoExecucaoTeste persistir) {
		List<T> objetosPersistir = getObjetosPersistir();

		persistir.setQuantidadeRegistro(objetosPersistir.size());

		persistir.iniciarExecucao();
		persistencia.save(objetosPersistir);
		persistir.finalizarExecucao();
	}

	protected List<T> executarOperacaoRecuperar(ResultadoExecucaoTeste recuperar) {
		recuperar.iniciarExecucao();
		List<T> findAllObjetos = (List<T>) persistencia.findAll(clazz);
		recuperar.finalizarExecucao();

		recuperar.setQuantidadeRegistro(findAllObjetos.size());

		return findAllObjetos;
	}

	protected void executarOperacaoAlterar(ResultadoExecucaoTeste alterar, List<T> objetos) {
		alterar.setQuantidadeRegistro(objetos.size());

		alterarDadosObjetos(objetos);

		alterar.iniciarExecucao();
		persistencia.save(objetos);
		alterar.finalizarExecucao();
	}

	protected void executarOperacaoRemover(ResultadoExecucaoTeste remover, List<T> objetos) {
		remover.setQuantidadeRegistro(objetos.size());
		remover.iniciarExecucao();
		persistencia.remove(objetos);
		remover.finalizarExecucao();
	}
}
