package br.com.mkacunha.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.anteros.persistence.metadata.annotation.type.GeneratedType;
import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.arquivo.ArquivoIterator;
import br.com.mkacunha.arquivo.Linha;

@Entity
@Table(name = "cidade")
@br.com.anteros.persistence.metadata.annotation.Entity
@br.com.anteros.persistence.metadata.annotation.Table(name = "cidade")
public class Cidade implements Serializable {

	@Id
	@Column(name = "id_cidade")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@br.com.anteros.persistence.metadata.annotation.Id
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_cidade")
	@br.com.anteros.persistence.metadata.annotation.GeneratedValue(strategy = GeneratedType.AUTO)
	private Long id;

	@Column(name = "ds_cidade", length = 45, nullable = false)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "ds_cidade", length = 45, required = true)
	private String nome;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.OneToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_estado", required = true)
	private Estado estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	@br.com.anteros.persistence.metadata.annotation.Temporal(br.com.anteros.persistence.metadata.annotation.type.TemporalType.DATE_TIME)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	public static Cidade of(String nome, Estado estado) {
		Cidade cidade = new Cidade();
		cidade.setNome(nome);
		cidade.setEstado(estado);
		return cidade;
	}

	public static List<Cidade> list(Estado estado) {
		List<Cidade> cidades = new ArrayList<>();

		ArquivoIterator arquivo = new Arquivo(Arquivo.CIDADES, estado.getSigla(), 1).iterator();

		while (arquivo.hasNext()) {
			Linha linha = arquivo.next();
			cidades.add(Cidade.of(linha.get(2), estado));
		}
		return cidades;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	@Override
	public String toString() {
		return id + " - " + nome + " - " + estado;
	}
}
