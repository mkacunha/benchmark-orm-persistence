package br.com.mkacunha.gerador.random;

public class GeradorTexto {
	Random random = new Random();

	private String[] caracteres = { "A", "B", "C", "D", " ", "E", "F", "G", "H", "I", " ", "J", "K", "L", "M", "N", "O",
			" ", "P", "Q", "R", "S", "T", " ", "U", "V", "W", "X", "Y", "Z", " " };

	public String get(int tamanho) {
		StringBuilder texto = new StringBuilder();

		for (int i = 0; i < tamanho; i++) {
			texto.append(caracteres[random.nextInt(caracteres.length)]);
		}

		return texto.toString();
	}

	public String get(int min, int max) {
		int tamanho = random.nextInt(min, max);
		return get(tamanho);
	}

}
