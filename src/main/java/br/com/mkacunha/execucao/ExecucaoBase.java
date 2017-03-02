package br.com.mkacunha.execucao;

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
		this.persistencia.clear();
		logger = Logger.getLogger(ExecucaoTeste.LOG_EXECUCAO);

		this.clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public abstract List<T> getObjetosPersistir();

	public abstract void alterarDadosObjetos(List<T> objetos);

	public abstract void fimExecucaoTeste();

	public void executar() {
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println();
		logger.info("Inicou execução de teste de operações referente a classe " + clazz.getSimpleName());

		executarOperacoes();

		logger.info("Fim da execução de testes de operações, chamada do método fimExecucaoTeste()");		
		fimExecucaoTeste();
		logger.info("Fim da execução do método fimExecucaoTeste()");
	}

	protected void executarOperacoes() {
		executarOperacaoPersistir();
		List<T> objetos = executarOperacaoRecuperar();
		executarOperacaoAlterar(objetos);
		executarOperacaoRemover(objetos);
	}

	protected void executarOperacaoPersistir() {
		ResultadoExecucaoOperacao persistir = execucao.add(clazz, TipoOperacao.INSERIR);
		List<T> objetosPersistir = getObjetosPersistir();

		persistir.setQuantidadeRegistro(objetosPersistir.size());

		persistir.iniciarExecucao();
		persistencia.save(objetosPersistir);
		persistir.finalizarExecucao();
	}

	protected List<T> executarOperacaoRecuperar() {
		ResultadoExecucaoOperacao recuperar = execucao.add(clazz, TipoOperacao.RECUPERAR);

		recuperar.iniciarExecucao();
		List<T> findAllObjetos = (List<T>) persistencia.findAll(clazz);
		recuperar.setQuantidadeRegistro(findAllObjetos.size());
		recuperar.finalizarExecucao();

		return findAllObjetos;
	}

	protected void executarOperacaoAlterar(List<T> objetos) {
		ResultadoExecucaoOperacao alterar = execucao.add(clazz, TipoOperacao.ALTERAR);

		alterar.setQuantidadeRegistro(objetos.size());

		alterarDadosObjetos(objetos);

		alterar.iniciarExecucao();
		persistencia.save(objetos);
		alterar.finalizarExecucao();
	}

	protected void executarOperacaoRemover(List<T> objetos) {
		ResultadoExecucaoOperacao remover = execucao.add(clazz, TipoOperacao.REMOVER);

		remover.setQuantidadeRegistro(objetos.size());
		remover.iniciarExecucao();
		persistencia.remove(objetos);
		remover.finalizarExecucao();
	}
}
