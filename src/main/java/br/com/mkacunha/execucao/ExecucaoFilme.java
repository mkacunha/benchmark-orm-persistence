package br.com.mkacunha.execucao;

import java.io.IOException;
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
	private ResultadoExecucaoTeste recuperarFilmeForcarLayzLitas;

	public ExecucaoFilme(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		idiomas = persistencia.findAll(Idioma.class);
		atores = persistencia.find(Ator.class, 1000);
		categorias = persistencia.findAll(Categoria.class);

		StringBuilder sb = new StringBuilder();
		sb.append("Recuperar por ");
		sb.append(quantidade);
		sb.append(" vez(es) objetos referentes a classe Filme e acessar as listas forçando o Lazy");

		recuperarFilmeForcarLayzLitas = execucao.add(sb.toString());
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
		List<Filme> filmes;

		recuperarFilmeForcarLayzLitas.iniciarExecucao();
		filmes = persistencia.findAll(Filme.class);
		filmes.forEach(filme -> {
			filme.getAtores().size();
			filme.getidiomas().size();
			filme.getCategorias().size();
		});
		recuperarFilmeForcarLayzLitas.finalizarExecucao();
		recuperarFilmeForcarLayzLitas.setQuantidadeRegistro(filmes.size());
	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Filme.list(1000, idiomas, atores, categorias));

		try {
			file.write(recuperarFilmeForcarLayzLitas.toString());
		} catch (IOException e) {
			logger.error("Erro ao salvar log da operação recuperar filme forçando lazy das listas: " + e.getMessage());
		}
	}

}
