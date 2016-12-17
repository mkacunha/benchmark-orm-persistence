package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Idioma;

public class ExecucaoIdioma extends ExecucaoBase<Idioma> {

	public ExecucaoIdioma(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);
	}

	@Override
	public List<Idioma> getObjetosPersistir() {
		return Idioma.list();
	}

	@Override
	public void alterarDadosObjetos(List<Idioma> objetos) {
		objetos.forEach(obj -> obj.setDataUltimaAlteracao(new Date()));
	}

	@Override
	public void executarAposOperacaoPersistencia() {

	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Idioma.list());
	}
}
