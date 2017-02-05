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

import br.com.mkacunha.gerador.random.GeradorNomes;

@Entity
@Table(name = "ator")
public class Ator {

	@Id
	@Column(name = "id_ator")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_nome", length = 100, nullable = false)
	private String nome;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	public static Ator of(String nome) {
		Ator ator = new Ator();
		ator.setNome(nome);
		return ator;
	}

	public static List<Ator> list(int quantidade) {
		List<Ator> atores = new ArrayList<>();

		GeradorNomes nomesAleatorios = new GeradorNomes();

		List<String> nomes = nomesAleatorios.get(quantidade);

		nomes.forEach(nome -> atores.add(Ator.of(nome)));

		return atores;
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
