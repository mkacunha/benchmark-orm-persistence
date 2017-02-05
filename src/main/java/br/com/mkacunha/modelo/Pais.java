package br.com.mkacunha.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.arquivo.ArquivoIterator;
import br.com.mkacunha.arquivo.Linha;
import br.com.mkacunha.gerador.random.GeradorTexto;

@Entity
@Table(name = "pais")
public class Pais implements Serializable {

	@Id
	@Column(name = "id_pais")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_nome", length = 60, nullable = false)
	private String nome;

	@Column(name = "ds_sigla", length = 10, nullable = false)
	private String sigla;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	@Lob
	@Column(name = "ds_pais", nullable = false)
	private String descricao;

	public static Pais of(String nome, String sigla, String descricao) {
		Pais pais = new Pais();
		pais.setNome(nome);
		pais.setSigla(sigla);
		pais.setDescricao(descricao);
		return pais;
	}

	public static List<Pais> list() {
		List<Pais> paises = new ArrayList<>();

		ArquivoIterator arquivo = new Arquivo(Arquivo.PAISES).iterator();
		GeradorTexto geradorTexto = new GeradorTexto();

		int cont = 0;

		while (arquivo.hasNext()) {
			cont++;

			if (cont == 240)
				cont = 0;

			Linha linha = arquivo.next();
			paises.add(Pais.of(linha.get(1), linha.get(0), geradorTexto.get(250)));
		}

		return paises;
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.nome + "(" + this.sigla + ")";
	}

}
