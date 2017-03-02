package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Idioma;

public class ExecucaoIdioma extends ExecucaoBase<Idioma> {

	public ExecucaoIdioma(ExecucaoTeste execucao) {
		super(execucao);
	}

	@Override
	public List<Idioma> getObjetosPersistir() {
		return Idioma.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
	}

	@Override
	public void alterarDadosObjetos(List<Idioma> objetos) {
		objetos.forEach(obj -> obj.setDataUltimaAlteracao(new Date()));
	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Idioma.list());
	}
}
