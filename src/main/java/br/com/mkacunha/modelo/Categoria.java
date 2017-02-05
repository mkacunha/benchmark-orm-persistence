package br.com.mkacunha.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mkacunha.arquivo.Arquivo;

@Entity
@Table(name = "categoria")
public class Categoria {

	@Id
	@Column(name = "id_categoria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_nome", length = 45, nullable = false)
	private String nome;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	public static Categoria of(String nome) {
		Categoria categoria = new Categoria();
		categoria.setNome(nome);
		return categoria;
	}

	public static List<Categoria> list() {
		List<Categoria> categorias = new ArrayList<>();

		new Arquivo(Arquivo.CATEGORIAS).linhas().forEach(linha -> categorias.add(Categoria.of(linha.get())));

		return categorias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	@Override
	public String toString() {
		return this.id + " - " + this.nome;
	}
}
