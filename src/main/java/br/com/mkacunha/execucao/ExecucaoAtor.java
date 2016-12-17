package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Ator;

public class ExecucaoAtor extends ExecucaoBase<Ator> {

	public ExecucaoAtor(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);
	}

	@Override
	public List<Ator> getObjetosPersistir() {
		return Ator.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
	}

	@Override
	public void alterarDadosObjetos(List<Ator> objetos) {
		objetos.forEach(obj -> {
			obj.setDataUltimaAlteracao(new Date());
		});
	}

	@Override
	public void executarAposOperacaoPersistencia() {
	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Ator.list(1000));
	}

}
