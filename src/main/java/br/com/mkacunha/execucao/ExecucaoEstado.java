package br.com.mkacunha.execucao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mkacunha.gerador.random.GeradorTexto;
import br.com.mkacunha.modelo.Estado;
import br.com.mkacunha.modelo.Pais;

public class ExecucaoEstado extends ExecucaoBase<Estado> {

	private List<Pais> paises;

	public ExecucaoEstado(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		paises = persistencia.findAll(Pais.class);
	}

	@Override
	public List<Estado> getObjetosPersistir() {
		List<Estado> estados = new ArrayList<>();

		paises.forEach(pais -> estados.addAll(Estado.list(pais)));

		return estados;
	}

	@Override
	public void alterarDadosObjetos(List<Estado> objetos) {
		GeradorTexto geradorTexto = new GeradorTexto();
		objetos.forEach(estado -> {
			estado.setDataUltimaAlteracao(new Date());
			estado.setDescricao(geradorTexto.get(600, 800));
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
