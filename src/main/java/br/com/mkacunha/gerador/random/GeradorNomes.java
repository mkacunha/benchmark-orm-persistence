package br.com.mkacunha.gerador.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.arquivo.Linha;

public class GeradorNomes {

	public List<String> get(int quantidade) {
		List<String> list = new ArrayList<>();

		Arquivo nomes = new Arquivo(Arquivo.NOMES);
		Arquivo sobrenomes = new Arquivo(Arquivo.SOBRENOMES);

		Random random = new Random();

		for (int i = 0; i < quantidade; i++) {
			List<Linha> nomesRandon = nomes.randon(random.nextInt(2) + 1);
			List<Linha> sobrenomesRandon = sobrenomes.randon(random.nextInt(2) + 1);

			StringBuilder builder = new StringBuilder();

			nomesRandon.forEach(linha -> {
				if (builder.length() > 0)
					builder.append(" ");
				builder.append(linha.get());
			});

			sobrenomesRandon.forEach(linha -> {
				builder.append(" ");
				builder.append(linha.get());
			});

			list.add(builder.toString());

		}

		return list;
	}

}
