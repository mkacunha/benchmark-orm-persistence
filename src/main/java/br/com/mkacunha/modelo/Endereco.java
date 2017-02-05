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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mkacunha.arquivo.Arquivo;
import br.com.mkacunha.arquivo.Linha;
import br.com.mkacunha.gerador.random.GeradorNomes;
import br.com.mkacunha.gerador.random.GeradorNumeros;

@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@Column(name = "id_endereco")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_logradouro", length = 100, nullable = false)
	private String logradouro;

	@Column(name = "nr_logradouro", length = 20, nullable = false)
	private String numero;

	@Column(name = "ds_bairro", length = 100, nullable = false)
	private String bairro;

	@Column(name = "ds_complemento", length = 250)
	private String complemento;

	@Column(name = "nr_cep", length = 8, nullable = false)
	private String cep;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade", nullable = false)
	private Cidade cidade;

	@Column(name = "nr_telefone", length = 20, nullable = false)
	private String numeroTelefone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	public static Endereco of(String logradouro, String numero, String bairro, String complemento, String cep,
			Cidade cidade, String numeroTelefone) {
		Endereco endereco = new Endereco();
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setBairro(bairro);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setNumeroTelefone(numeroTelefone);
		return endereco;
	}

	public static Endereco of(Cidade cidade, String logradouro, String numero, String bairro, String complemento,
			String cep, String numeroTelefone) {
		Endereco endereco = new Endereco();

		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setBairro(bairro);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setNumeroTelefone(numeroTelefone);
		return endereco;
	}

	public static List<Endereco> list(int quantidade, List<Cidade> cidades) {
		List<Endereco> enderecos = new ArrayList<>();

		List<Linha> tiposLogradouro = new Arquivo(Arquivo.TIPOS_LOGRADOURO).randon(quantidade);
		List<String> logradouros = new GeradorNomes().get(quantidade);
		List<Linha> bairros = new Arquivo(Arquivo.BAIRROS).randon(quantidade);
		List<Linha> complementos = new Arquivo(Arquivo.COMPLEMENTOS_LOGRADOURO).randon(quantidade);
		List<Linha> ceps = new Arquivo(Arquivo.CEPS).randon(quantidade);
		List<String> telefones = new GeradorNumeros().get(quantidade, 10, 11);

		Random random = new Random();

		for (int i = 0; i < quantidade; i++) {
			Cidade cidade = cidades.get(random.nextInt(cidades.size()));

			enderecos.add(Endereco.of(cidade, tiposLogradouro.get(i).get() + " " + logradouros.get(i),
					String.valueOf(random.nextInt(10000)), bairros.get(i).get(), complementos.get(i).get(),
					ceps.get(i).get(), telefones.get(i)));
		}

		return enderecos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

}
