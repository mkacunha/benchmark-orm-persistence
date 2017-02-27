package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Estado;

public class ExecucaoCidade extends ExecucaoBase<Cidade> {

	private List<Estado> estados;

	public ExecucaoCidade(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		estados = persistencia.find(Estado.class, 100);
	}

	@Override
	public List<Cidade> getObjetosPersistir() {
		return Cidade.list(estados, ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
	}

	@Override
	public void alterarDadosObjetos(List<Cidade> objetos) {
		objetos.forEach(obj -> {
			obj.setDataUltimaAlteracao(new Date());
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
