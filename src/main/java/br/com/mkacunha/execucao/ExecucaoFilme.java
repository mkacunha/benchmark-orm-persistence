package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.gerador.random.Random;
import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.modelo.Categoria;
import br.com.mkacunha.modelo.Filme;
import br.com.mkacunha.modelo.Idioma;

public class ExecucaoFilme extends ExecucaoBase<Filme> {

	private List<Idioma> idiomas;
	private List<Ator> atores;
	private List<Categoria> categorias;
	private Random random = new Random();

	public ExecucaoFilme(ExecucaoTeste execucao) {
		super(execucao);

		idiomas = persistencia.find(Idioma.class, 1000);
		atores = persistencia.find(Ator.class, 1000);
		categorias = persistencia.find(Categoria.class, 1000);
	}

	@Override
	public List<Filme> getObjetosPersistir() {
		return Filme.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE, idiomas, atores, categorias);
	}

	@Override
	public void alterarDadosObjetos(List<Filme> objetos) {
		objetos.forEach(obj -> {
			obj.setDataUltimaAlteracao(new Date());
			obj.setValorAluguel(random.nextMonetaryValue(5, 20));
		});

	}

	@Override
	public void executarAposOperacaoPersistencia() {

	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Filme.list(1000, idiomas, atores, categorias));
	}

}
