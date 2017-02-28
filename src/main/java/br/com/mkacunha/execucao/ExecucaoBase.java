package br.com.mkacunha.execucao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.mkacunha.operacao.ResultadoExecucaoOperacao;
import br.com.mkacunha.operacao.TipoOperacao;
import br.com.mkacunha.persistencia.Persistencia;

public abstract class ExecucaoBase<T> {

	private Class<?> clazz;
	protected ExecucaoTeste execucao;
	protected Persistencia persistencia;
	protected Logger logger;

	public ExecucaoBase(ExecucaoTeste execucao) {
		this.execucao = execucao;
		this.persistencia = execucao.getPersistencia();
		logger = Logger.getLogger(ExecucaoTeste.LOG_EXECUCAO);

		this.clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public abstract List<T> getObjetosPersistir();

	public abstract void alterarDadosObjetos(List<T> objetos);

	public abstract void fimExecucaoTeste();

	public abstract void executarAposOperacaoPersistencia();

	public void executar() {
		logger.info("Inicou execução de teste de operações referente a classe " + clazz.getSimpleName());

		ResultadoExecucaoOperacao persistir = execucao.add(clazz, TipoOperacao.INSERIR);
		ResultadoExecucaoOperacao recuperar = execucao.add(clazz, TipoOperacao.RECUPERAR);
		ResultadoExecucaoOperacao alterar = execucao.add(clazz, TipoOperacao.ALTERAR);
		ResultadoExecucaoOperacao remover = execucao.add(clazz, TipoOperacao.DELETAR);

		executarOperacoes(persistir, recuperar, alterar, remover);

		logger.info("Fim da execução de testes de operações, chamada do método fimExecucaoTeste()");
		fimExecucaoTeste();
	}

	protected void executarOperacoes(ResultadoExecucaoOperacao persistir, ResultadoExecucaoOperacao recuperar,
			ResultadoExecucaoOperacao alterar, ResultadoExecucaoOperacao remover) {
		executarOperacaoPersistir(persistir);
		executarAposOperacaoPersistencia();
		List<T> objetos = executarOperacaoRecuperar(recuperar);
		executarOperacaoAlterar(alterar, objetos);
		executarOperacaoRemover(remover, objetos);
	}

	protected void executarOperacaoPersistir(ResultadoExecucaoOperacao persistir) {
		List<T> objetosPersistir = getObjetosPersistir();

		persistir.setQuantidadeRegistro(objetosPersistir.size());

		persistir.iniciarExecucao();
		persistencia.save(objetosPersistir);
		persistir.finalizarExecucao();
	}

	protected List<T> executarOperacaoRecuperar(ResultadoExecucaoOperacao recuperar) {
		recuperar.iniciarExecucao();
		List<T> findAllObjetos = (List<T>) persistencia.findAll(clazz);
		recuperar.setQuantidadeRegistro(findAllObjetos.size());
		recuperar.finalizarExecucao();

		return findAllObjetos;
	}

	protected void executarOperacaoAlterar(ResultadoExecucaoOperacao alterar, List<T> objetos) {
		alterar.setQuantidadeRegistro(objetos.size());

		alterarDadosObjetos(objetos);

		alterar.iniciarExecucao();
		persistencia.save(objetos);
		alterar.finalizarExecucao();
	}

	protected void executarOperacaoRemover(ResultadoExecucaoOperacao remover, List<T> objetos) {
		remover.setQuantidadeRegistro(objetos.size());
		remover.iniciarExecucao();
		persistencia.remove(objetos);
		remover.finalizarExecucao();
	}
}
