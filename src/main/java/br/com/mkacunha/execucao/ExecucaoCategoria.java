package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Categoria;

public class ExecucaoCategoria extends ExecucaoBase<Categoria> {

	public ExecucaoCategoria(ExecucaoTeste execucao) {
		super(execucao);
	}

	@Override
	public List<Categoria> getObjetosPersistir() {
		return Categoria.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
	}

	@Override
	public void alterarDadosObjetos(List<Categoria> objetos) {
		objetos.forEach(obj -> obj.setDataUltimaAlteracao(new Date()));
	}

	@Override
	public void executarAposOperacaoPersistencia() {

	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Categoria.list());
	}
}
