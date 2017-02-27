package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.gerador.random.GeradorTexto;
import br.com.mkacunha.modelo.Pais;

public class ExecucaoPais extends ExecucaoBase<Pais> {

	public ExecucaoPais(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);
	}

	@Override
	public List<Pais> getObjetosPersistir() {
		return Pais.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
	}

	@Override
	public void alterarDadosObjetos(List<Pais> objetos) {
		GeradorTexto geradorTexto = new GeradorTexto();

		objetos.forEach(p -> {
			p.setDescricao(geradorTexto.get(500));
			p.setDataUltimaAlteracao(new Date());
		});
	}

	@Override
	public void executarAposOperacaoPersistencia() {
	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(getObjetosPersistir());
	}

}
