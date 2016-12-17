package br.com.mkacunha.arquivo;

import java.util.Iterator;
import java.util.List;

public class ArquivoIterator implements Iterator<Linha> {

	private Iterator<Linha> linhas;

	public ArquivoIterator(List<Linha> linhas) {
		this.linhas = linhas.iterator();
	}

	@Override
	public boolean hasNext() {
		return linhas.hasNext();
	}

	@Override
	public Linha next() {
		return linhas.next();
	}
}
