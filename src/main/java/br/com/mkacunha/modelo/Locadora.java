package br.com.mkacunha.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.anteros.persistence.metadata.annotation.type.GeneratedType;

@Entity
@Table(name = "locadora")
@br.com.anteros.persistence.metadata.annotation.Entity
@br.com.anteros.persistence.metadata.annotation.Table(name = "locadora")
public class Locadora implements Serializable {

	@Id
	@Column(name = "id_locadora")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@br.com.anteros.persistence.metadata.annotation.Id
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_locadora")
	@br.com.anteros.persistence.metadata.annotation.GeneratedValue(strategy = GeneratedType.AUTO)
	private Long id;

	@Column(name = "ds_nome", length = 100, nullable = false)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "ds_nome", length = 100, required = true)
	private String nome;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.OneToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER, cascade = br.com.anteros.persistence.metadata.annotation.type.CascadeType.ALL)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_endereco", required = true)
	private Endereco endereco;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_gerente", referencedColumnName = "id_funcionario")
	@br.com.anteros.persistence.metadata.annotation.OneToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_gerente", inversedColumn = "id_funcionario")
	private Funcionario gerente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	@br.com.anteros.persistence.metadata.annotation.Temporal(br.com.anteros.persistence.metadata.annotation.type.TemporalType.DATE_TIME)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	@OneToMany(mappedBy = "locadora", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@br.com.anteros.persistence.metadata.annotation.OneToMany(mappedBy = "locadora", cascade = br.com.anteros.persistence.metadata.annotation.type.CascadeType.NONE, fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.LAZY)
	private Set<Funcionario> funcionarios;

	public static Locadora of(String nome, Endereco endereco, Funcionario gerente) {
		Locadora locadora = new Locadora();
		locadora.setNome(nome);
		locadora.setEndereco(endereco);
		locadora.setGerente(gerente);
		return locadora;
	}

	public static List<Locadora> list(int quantidade, List<Cidade> cidades) {
		List<Locadora> locadoras = new ArrayList<>();
		List<Endereco> enderecos = Endereco.list(quantidade, cidades);

		for (int i = 0; i < quantidade; i++) {
			Endereco endereco = enderecos.get(i);

			StringBuilder nome = new StringBuilder();
			nome.append("Locadora ");
			nome.append(endereco.getCidade().getNome());
			nome.append(" ");
			nome.append(i);

			locadoras.add(Locadora.of(nome.toString(), endereco, null));
		}

		return locadoras;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Funcionario getGerente() {
		return gerente;
	}

	public void setGerente(Funcionario gerente) {
		this.gerente = gerente;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Set<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public String toString() {
		return id + " - " + nome;
	}

}
