package br.com.mkacunha.gerador.random;

import java.util.ArrayList;
import java.util.List;

public class GeradorNumeros {

	public List<String> get(int quantidade, int tamanhoMinimo, int tamanhoMaximo) {
		List<String> telefones = new ArrayList<>();

		Random random = new Random();

		for (int i = 0; i < quantidade; i++) {
			StringBuilder builder = new StringBuilder();

			int quantidadeNumeros = random.nextInt(tamanhoMinimo, tamanhoMaximo);

			while (builder.length() != quantidadeNumeros) {
				builder.append(String.valueOf(random.nextInt(10)));
			}

			telefones.add(builder.toString());
		}

		return telefones;
	}

}
