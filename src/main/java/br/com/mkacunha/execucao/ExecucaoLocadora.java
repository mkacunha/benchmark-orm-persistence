package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Locadora;

public class ExecucaoLocadora extends ExecucaoBase<Locadora> {

	private List<Cidade> cidades;

	public ExecucaoLocadora(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		cidades = persistencia.findAll(Cidade.class);
	}

	@Override
	public List<Locadora> getObjetosPersistir() {
		return Locadora.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE, cidades);
	}

	@Override
	public void alterarDadosObjetos(List<Locadora> objetos) {
		objetos.forEach(obj -> {
			obj.setDataUltimaAlteracao(new Date());
		});
	}

	@Override
	public void executarAposOperacaoPersistencia() {

	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Locadora.list(1000, cidades));
	}
}
