package br.com.mkacunha.arquivo;

import java.util.regex.Pattern;

public class Linha {

	private String[] split;
	private String linha;

	public Linha(String linha) {
		split = linha.split(Pattern.quote("|"));
		this.linha = linha;
	}

	public String get(int index) {
		return split[index];
	}

	public String get() {
		return linha;
	}
}
