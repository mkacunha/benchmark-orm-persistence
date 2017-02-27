package br.com.mkacunha.gerador.random;

import java.util.ArrayList;
import java.util.List;

public class RandomList<T> {

	private Random random;

	public RandomList() {
		random = new Random();
	}

	public List<T> list(List<T> list, int min, int max) {
		List<T> aux = new ArrayList<>();

		if (list == null)
			throw new RuntimeException("Lista passada por parâmeto não pode ser nula");

		if (list.size() == 0)
			throw new RuntimeException("Lista passada por parâmetro não pode estar vazia");

		if (min > max)
			throw new RuntimeException(
					"Parâmetro min não pode ser maior que parâmetro max (" + min + " - " + max + ")");

		if (list.size() < max)
			throw new RuntimeException("Quantidade de registros na lista não pode ser menor que o parâmetro max");

		int quant = random.nextInt(min, max);

		for (int i = 0; i < quant; i++) {
			aux.add(list.get(i));
		}
		return aux;
	}

	public List<T> list(List<T> list, int quantidade) {
		List<T> aux = new ArrayList<>();
		int size = list.size();

		for (int i = 0; i < quantidade; i++) {
			aux.add(list.get(random.nextInt(size)));
		}

		return aux;
	}
}
