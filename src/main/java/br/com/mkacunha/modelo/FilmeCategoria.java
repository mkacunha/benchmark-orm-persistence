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
@Table(name = "filmecategoria")
public class FilmeCategoria {

	@Id
	@Column(name = "id_filmecategoria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_filme", nullable = false)
	private Filme filme;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;

	public static FilmeCategoria of(Filme filme, Categoria categoria) {
		FilmeCategoria filmeCategoria = new FilmeCategoria();
		filmeCategoria.setFilme(filme);
		filmeCategoria.setCategoria(categoria);
		return filmeCategoria;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return this.categoria.toString();
	}
}
