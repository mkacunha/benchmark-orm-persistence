package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Categoria;

public class ExecucaoCategoria extends ExecucaoBase<Categoria> {

	public ExecucaoCategoria(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);
	}

	@Override
	public List<Categoria> getObjetosPersistir() {
		return Categoria.list();
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