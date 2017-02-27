package br.com.mkacunha.teste;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.gerador.random.Random;
import br.com.mkacunha.gerador.random.RandomList;
import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.modelo.Pais;
import br.com.mkacunha.operacao.ResultadoExecucaoOperacao;
import br.com.mkacunha.persistencia.EclipseLinkPersistencia;

public class Main {

	public static void main(String[] args) {
		
		new Main().testeRandom();
		

	}

	public void testeRandom(){
		List<Pais> paises = Pais.list();
		
		System.out.println(paises.size());
		
		List<Pais> list = new RandomList<Pais>().list(paises, 100000);
		
		
		System.out.println(list.size());
		
	}
	
	public void testePersistencia(){
		// HibernatePersistencia persistencia = new HibernatePersistencia();
		 EclipseLinkPersistencia persistencia = new EclipseLinkPersistencia();
		
	//	ResultadoExecucaoOperacao resultado = new ResultadoExecucaoOperacao("Teste persistir");
		List<Ator> list = Ator.list(100000);

//		resultado.setQuantidadeRegistro(list.size());  0.598
//		resultado.iniciarExecucao();
//		persistencia.save(list);
//		resultado.finalizarExecucao();
//		System.out.println(resultado);

//		resultado = new ResultadoExecucaoOperacao("Teste recuperar");
//		resultado.iniciarExecucao();
//		List<Ator> findAll = persistencia.findAll(Ator.class);
//		resultado.finalizarExecucao();
//		resultado.setQuantidadeRegistro(findAll.size());
//		System.out.println(resultado);

		// resultado = new ResultadoExecucaoTeste("Teste alterar");
		// List<Ator> list = persistencia.findAll(Ator.class);
		// resultado.setQuantidadeRegistro(findAll.size());

//		findAll.forEach(o -> o.setDataUltimaAlteracao(new Date()));
//
//		resultado.iniciarExecucao();
//		persistencia.save(findAll);
//		resultado.finalizarExecucao();
//		System.out.println(resultado);

//		resultado = new ResultadoExecucaoTeste("Teste remover");
//		list = persistencia.findAll(Ator.class);
//		resultado.setQuantidadeRegistro(list.size());
//		resultado.iniciarExecucao();
//		persistencia.remove(list);
//		resultado.finalizarExecucao();
//		System.out.println(resultado);
		
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
