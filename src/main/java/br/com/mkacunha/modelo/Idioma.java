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
import br.com.mkacunha.arquivo.Linha;
import br.com.mkacunha.gerador.random.Random;

@Entity
@Table(name = "idioma")
public class Idioma {

	@Id
	@Column(name = "id_idioma")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_nome", length = 45, nullable = false)
	private String nome;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	public static Idioma of(String nome) {
		Idioma idioma = new Idioma();
		idioma.setNome(nome);
		return idioma;
	}

	public static List<Idioma> list() {
		List<Idioma> idiomas = new ArrayList<>();

		List<Linha> linhas = new Arquivo(Arquivo.IDIOMAS).linhas();

		linhas.forEach(linha -> idiomas.add(Idioma.of(linha.get())));

		return idiomas;
	}

	public static List<Idioma> list(int quantidade) {
		List<Idioma> idiomas = Idioma.list();

		if (idiomas == null || idiomas.isEmpty())
			return null;

		List<Idioma> aux = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < quantidade; i++) {
			int nextInt = random.nextInt(idiomas.size());

			Idioma idioma = idiomas.get(nextInt);

			aux.add(Idioma.of(idioma.getNome()));
		}

		return aux;
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
