package br.com.mkacunha.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
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

import br.com.anteros.persistence.metadata.annotation.BooleanValue;
import br.com.anteros.persistence.metadata.annotation.type.BooleanType;
import br.com.anteros.persistence.metadata.annotation.type.GeneratedType;
import br.com.mkacunha.gerador.random.GeradorNomes;

@Entity
@Table(name = "cliente")
@br.com.anteros.persistence.metadata.annotation.Entity
@br.com.anteros.persistence.metadata.annotation.Table(name = "cliente")
public class Cliente implements Serializable {

	@Id
	@Column(name = "id_cliente")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@br.com.anteros.persistence.metadata.annotation.Id
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_cliente")
	@br.com.anteros.persistence.metadata.annotation.GeneratedValue(strategy = GeneratedType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_locadora", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.OneToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_locadora", required = true)
	private Locadora locadora;

	@Column(name = "ds_nome", length = 100, nullable = false)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "ds_nome", length = 100, required = true)
	private String nome;

	@Column(name = "ds_email", length = 100)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "ds_email", length = 100)
	private String email;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.OneToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER, cascade = br.com.anteros.persistence.metadata.annotation.type.CascadeType.ALL)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_endereco", required = true)
	private Endereco endereco;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.Temporal(br.com.anteros.persistence.metadata.annotation.type.TemporalType.DATE_TIME)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "dt_criacao", required = true)
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	@br.com.anteros.persistence.metadata.annotation.Temporal(br.com.anteros.persistence.metadata.annotation.type.TemporalType.DATE_TIME)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	@Column(name = "bo_ativo", columnDefinition = "varchar(1)", nullable = false)
	@BooleanValue(trueValue = "1", falseValue = "0", type = BooleanType.STRING)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "bo_ativo", length = 1, required = true)
	private Boolean ativo;

	public static Cliente of(Locadora locadora, String nome, String email, Endereco endereco, Boolean ativo) {
		Cliente cliente = new Cliente();
		cliente.setLocadora(locadora);
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setEndereco(endereco);
		cliente.setAtivo(ativo);
		cliente.setDataCriacao(new Date());
		return cliente;
	}

	public static List<Cliente> list(int quantidade, List<Locadora> locadoras, List<Cidade> cidades) {
		List<Cliente> clientes = new ArrayList<>();

		List<Endereco> enderecos = Endereco.list(quantidade, cidades);
		List<String> nomes = new GeradorNomes().get(quantidade);

		Random random = new Random();

		for (int i = 0; i < quantidade; i++) {
			Locadora locadora = locadoras.get(random.nextInt(locadoras.size()));
			String nome = nomes.get(i);
			Endereco endereco = enderecos.get(i);

			StringBuilder email = new StringBuilder();
			String[] split = nome.split(" ");
			email.append(split[0]);
			email.append(split[split.length - 1]);
			email.append("@email.com");

			clientes.add(Cliente.of(locadora, nome, email.toString(), endereco, true));
		}

		return clientes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locadora getLocadora() {
		return locadora;
	}

	public void setLocadora(Locadora locadora) {
		this.locadora = locadora;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
