package br.com.mkacunha.teste;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.execucao.ResultadoExecucaoTeste;
import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.persistencia.AnterosPersistencia;

public class Main {

	public static void main(String[] args) {

		// HibernatePersistencia persistencia = new HibernatePersistencia();
		// EclipseLinkPersistencia persistencia = new EclipseLinkPersistencia();
		 AnterosPersistencia persistencia = new AnterosPersistencia();
		
		ResultadoExecucaoTeste resultado = new ResultadoExecucaoTeste("Teste persistir");
		List<Ator> list = Ator.list(100000);

		resultado.setQuantidadeRegistro(list.size());
		resultado.iniciarExecucao();
		persistencia.save(list);
		resultado.finalizarExecucao();
		System.out.println(resultado);

		resultado = new ResultadoExecucaoTeste("Teste recuperar");
		resultado.iniciarExecucao();
		List<Ator> findAll = persistencia.findAll(Ator.class);
		resultado.finalizarExecucao();
		resultado.setQuantidadeRegistro(findAll.size());
		System.out.println(resultado);

		resultado = new ResultadoExecucaoTeste("Teste alterar");
		// List<Ator> list = persistencia.findAll(Ator.class);
		resultado.setQuantidadeRegistro(findAll.size());

		findAll.forEach(o -> o.setDataUltimaAlteracao(new Date()));

		resultado.iniciarExecucao();
		persistencia.save(findAll);
		resultado.finalizarExecucao();
		System.out.println(resultado);

		resultado = new ResultadoExecucaoTeste("Teste remover");
		list = persistencia.findAll(Ator.class);
		resultado.setQuantidadeRegistro(list.size());
		resultado.iniciarExecucao();
		persistencia.remove(list);
		resultado.finalizarExecucao();
		System.out.println(resultado);
	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public void reescreverArquivo() {
		try {

			FileWriter file = new FileWriter("/Volumes/Mac/teste.txt", true);

			Arquivo arquivo = new Arquivo(Arquivo.IDIOMAS);

			List<String> idiomas = new ArrayList<>();

			arquivo.linhas().forEach(linha -> {
				try {
					String idioma = linha.get();
					idioma = idioma.substring(0, idioma.indexOf("–")).trim();

					if (!idiomas.contains(idioma))
						idiomas.add(idioma);
				} catch (Exception e) {
					// TODO: handle exception
				}
			});

			try {
				idiomas.forEach(linha -> {
					try {
						file.write(linha + "\n");
					} catch (Exception e) {

					}
				});
			} catch (Exception e) {

			}

			file.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}