package br.com.mkacunha.execucao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Estado;

public class ExecucaoCidade extends ExecucaoBase<Cidade> {

	private List<Estado> estados;

	public ExecucaoCidade(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		estados = persistencia.findAll(Estado.class);
	}

	@Override
	public List<Cidade> getObjetosPersistir() {
		List<Cidade> cidades = new ArrayList<>();
		estados.forEach(estado -> cidades.addAll(Cidade.list(estado)));

		return cidades;
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
