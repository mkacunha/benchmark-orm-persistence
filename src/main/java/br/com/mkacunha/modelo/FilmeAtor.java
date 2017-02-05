package br.com.mkacunha.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filmeator")
public class FilmeAtor {

	@Id
	@Column(name = "id_filmeator")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_filme", nullable = false)
	private Filme filme;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ator", nullable = false)
	private Ator ator;

	public static FilmeAtor of(Filme filme, Ator ator) {
		FilmeAtor filmeAtor = new FilmeAtor();
		filmeAtor.setFilme(filme);
		filmeAtor.setAtor(ator);
		return filmeAtor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Ator getAtor() {
		return ator;
	}

	public void setAtor(Ator ator) {
		this.ator = ator;
	}

	@Override
	public String toString() {
		return this.ator.toString();
	}

}
