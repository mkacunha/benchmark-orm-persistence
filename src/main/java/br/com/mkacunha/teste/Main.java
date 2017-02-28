package br.com.mkacunha.teste;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.execucao.ExecucaoTeste;
import br.com.mkacunha.gerador.random.Random;
import br.com.mkacunha.gerador.random.RandomList;
import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Estado;
import br.com.mkacunha.modelo.Idioma;
import br.com.mkacunha.modelo.Pais;
import br.com.mkacunha.operacao.ResultadoExecucaoOperacao;
import br.com.mkacunha.operacao.TipoOperacao;
import br.com.mkacunha.persistencia.EclipseLinkPersistencia;
import br.com.mkacunha.persistencia.HibernatePersistencia;
import br.com.mkacunha.persistencia.Persistencia;

public class Main {

	public static void main(String[] args) {

//		new Main().persistenciaAtor();
		
		HibernatePersistencia hibernatePersistencia = new HibernatePersistencia();

		List<Idioma> idioma = Idioma.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
		
		System.out.println("Idiomas: " + idioma.size());
		
		hibernatePersistencia.removeAll(Idioma.class);
		
		hibernatePersistencia.save(idioma);
		
		List<Idioma> findAll = hibernatePersistencia.findAll(Idioma.class);
		
		
		System.out.println(findAll.size());

	}

	public void persistenciaAtor() {

		Persistencia persistencia;

		if (new Scanner(System.in).nextInt() == 1)
			persistencia = new EclipseLinkPersistencia();
		else
			persistencia = new HibernatePersistencia();
		
		ResultadoExecucaoOperacao inserir = new ResultadoExecucaoOperacao(TipoOperacao.INSERIR);

		inserir.iniciarExecucao();
		//persistencia.save(Ator.list(100000));
		inserir.finalizarExecucao();

		ResultadoExecucaoOperacao recuperar = new ResultadoExecucaoOperacao(TipoOperacao.RECUPERAR);

		recuperar.iniciarExecucao();
		List<Ator> findAll = persistencia.findAll(Ator.class);

		System.out.println(findAll.get(99999));
		recuperar.finalizarExecucao();

		System.out.println(inserir);
		System.out.println(recuperar);

	}

	public void testeRandom() {
		List<Pais> paises = Pais.list();

		System.out.println(paises.size());

	}

	public void testePersistencia() {
		// HibernatePersistencia persistencia = new HibernatePersistencia();
		EclipseLinkPersistencia persistencia = new EclipseLinkPersistencia();

		// ResultadoExecucaoOperacao resultado = new
		// ResultadoExecucaoOperacao("Teste persistir");
		List<Ator> list = Ator.list(100000);

		// resultado.setQuantidadeRegistro(list.size()); 0.598
		// resultado.iniciarExecucao();
		// persistencia.save(list);
		// resultado.finalizarExecucao();
		// System.out.println(resultado);

		// resultado = new ResultadoExecucaoOperacao("Teste recuperar");
		// resultado.iniciarExecucao();
		// List<Ator> findAll = persistencia.findAll(Ator.class);
		// resultado.finalizarExecucao();
		// resultado.setQuantidadeRegistro(findAll.size());
		// System.out.println(resultado);

		// resultado = new ResultadoExecucaoTeste("Teste alterar");
		// List<Ator> list = persistencia.findAll(Ator.class);
		// resultado.setQuantidadeRegistro(findAll.size());

		// findAll.forEach(o -> o.setDataUltimaAlteracao(new Date()));
		//
		// resultado.iniciarExecucao();
		// persistencia.save(findAll);
		// resultado.finalizarExecucao();
		// System.out.println(resultado);

		// resultado = new ResultadoExecucaoTeste("Teste remover");
		// list = persistencia.findAll(Ator.class);
		// resultado.setQuantidadeRegistro(list.size());
		// resultado.iniciarExecucao();
		// persistencia.remove(list);
		// resultado.finalizarExecucao();
		// System.out.println(resultado);

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
