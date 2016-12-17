package br.com.mkacunha.arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.mkacunha.gerador.random.Random;

public class Arquivo {
	public static final String PAISES = "/listasdados/paises.txt";
	public static final String ESTADOS = "/listasdados/estados.txt";
	public static final String CIDADES = "/listasdados/cidades.txt";
	public static final String CATEGORIAS = "/listasdados/categorias.txt";
	public static final String NOMES = "/listasdados/nomes.txt";
	public static final String SOBRENOMES = "/listasdados/sobrenomes.txt";
	public static final String TIPOS_LOGRADOURO = "/listasdados/tiposlogradouro.txt";
	public static final String COMPLEMENTOS_LOGRADOURO = "/listasdados/complementoslogradouro.txt";
	public static final String BAIRROS = "/listasdados/bairros.txt";
	public static final String CEPS = "/listasdados/ceps.txt";
	public static final String IDIOMAS = "/listasdados/idiomas.txt";
	public static final String FILMES = "/listasdados/filmes.txt";

	private Random random;
	private List<Linha> linhas;

	public Arquivo(String filename) {
		initLinhas(filename, false, null, 0);
	}

	public Arquivo(String filename, String filter, int indexFilter) {
		initLinhas(filename, true, filter, indexFilter);
	}

	private void initLinhas(String fileName, boolean isFiltered, String filter, int indexFilter) {
		try {
			String file = ArquivoIterator.class.getResource(fileName).getFile();

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			Stream<String> stream;
			if (isFiltered)
				stream = bufferedReader.lines().filter(line -> line.split(Pattern.quote("|"))[indexFilter].toUpperCase()
						.trim().equals(filter.toUpperCase().trim()));
			else
				stream = bufferedReader.lines();

			linhas = new ArrayList<>();
			stream.collect(Collectors.toList()).forEach(linha -> linhas.add(new Linha(linha)));

			stream.close();
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Linha> linhas() {
		return linhas;
	}

	public ArquivoIterator iterator() {
		return new ArquivoIterator(linhas);
	}

	public List<Linha> randon(int quantidade) {
		List<Linha> aux = new ArrayList<>();

		int size = linhas.size();
		if (linhas != null && size > 0) {
			if (random == null)
				random = new Random();

			for (int i = 0; i < quantidade; i++) {
				aux.add(linhas.get(random.nextInt(size)));
			}
		}

		return aux;
	}

	public Linha randon() {
		List<Linha> linhas = randon(1);
		if (linhas.size() == 1)
			return linhas.get(0);
		return new Linha("");
	}
}
