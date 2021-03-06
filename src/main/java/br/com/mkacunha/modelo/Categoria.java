package br.com.mkacunha.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.gerador.random.RandomList;

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

	public static List<Categoria> list(int quantidade) {
		List<Categoria> categorias = Categoria.list();

		if (categorias != null && !categorias.isEmpty()){
			List<Categoria> aux = new ArrayList<>();
			int size = categorias.size();
			Random random = new Random();
			
			for (int i = 0; i < quantidade; i++) {
				Categoria categoria = categorias.get(random.nextInt(size));
				aux.add(Categoria.of(categoria.getNome()));
			}
			return aux;
		}

		return null;
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
