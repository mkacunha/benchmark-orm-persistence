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
@Table(name = "filmeidioma")
public class FilmeIdioma {

	@Id
	@Column(name = "id_filmeidioma")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_filme", nullable = false)
	private Filme filme;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_idioma", nullable = false)
	private Idioma idioma;

	public static FilmeIdioma of(Filme filme, Idioma idioma) {
		FilmeIdioma filmeIdioma = new FilmeIdioma();
		filmeIdioma.setFilme(filme);
		filmeIdioma.setidioma(idioma);
		return filmeIdioma;
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

	public Idioma getidioma() {
		return idioma;
	}

	public void setidioma(Idioma idioma) {
		this.idioma = idioma;
	}

	@Override
	public String toString() {
		return this.idioma.toString();
	}

}
