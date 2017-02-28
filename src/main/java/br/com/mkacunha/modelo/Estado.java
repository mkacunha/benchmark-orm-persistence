package br.com.mkacunha.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.arquivo.ArquivoIterator;
import br.com.mkacunha.arquivo.Linha;
import br.com.mkacunha.gerador.random.GeradorTexto;
import br.com.mkacunha.gerador.random.RandomList;

@Entity
@Table(name = "estado")
public class Estado {

	@Id
	@Column(name = "id_estado")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_nome", length = 45, nullable = false)
	private String nome;

	@Column(name = "ds_sigla", length = 10, nullable = false)
	private String sigla;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pais", nullable = false)
	private Pais pais;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	@Lob
	@Column(name = "ds_estado", nullable = false)
	private String descricao;

	public static Estado of(String nome, String sigla, Pais pais, String descricao) {
		Estado estado = new Estado();
		estado.setNome(nome);
		estado.setSigla(sigla);
		estado.setPais(pais);
		estado.setDescricao(descricao);
		return estado;
	}

	public static List<Estado> list(List<Pais> paises) {
		List<Estado> estados = new ArrayList<>();

		paises.forEach(pais -> {
			ArquivoIterator arquivo = new Arquivo(Arquivo.ESTADOS, pais.getNome(), 0).iterator();
			GeradorTexto geradorTexto = new GeradorTexto();
			while (arquivo.hasNext()) {
				Linha linha = arquivo.next();
				estados.add(Estado.of(linha.get(2), linha.get(1), pais, geradorTexto.get(200, 500)));
			}
		});

		return estados;
	}

	public static List<Estado> list(List<Pais> paises, int quantidade) {
		List<Estado> estados = Estado.list(paises);
		if (estados != null && !estados.isEmpty()) {
			List<Estado> aux = new ArrayList<>();
			int size = estados.size();
			Random random = new Random();

			for (int i = 0; i < quantidade; i++) {
				Estado estado = estados.get(random.nextInt(size));
				aux.add(Estado.of(estado.getNome(), estado.getSigla(), estado.getPais(), estado.getDescricao()));
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
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
		return id + " - " + nome + " (" + pais + ")";
	}
}
