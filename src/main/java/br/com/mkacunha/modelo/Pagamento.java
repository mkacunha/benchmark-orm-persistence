package br.com.mkacunha.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

import br.com.anteros.persistence.metadata.annotation.type.GeneratedType;

@Entity
@Table(name = "pagamento")
@br.com.anteros.persistence.metadata.annotation.Entity
@br.com.anteros.persistence.metadata.annotation.Table(name = "pagamento")
public class Pagamento implements Serializable {

	@Id
	@Column(name = "id_pagamento")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@br.com.anteros.persistence.metadata.annotation.Id
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_pagamento")
	@br.com.anteros.persistence.metadata.annotation.GeneratedValue(strategy = GeneratedType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.ManyToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_cliente", required = true)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcionario", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.ManyToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_funcionario", required = true)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_aluguel", nullable = false)
	@br.com.anteros.persistence.metadata.annotation.ManyToOne(fetch = br.com.anteros.persistence.metadata.annotation.type.FetchType.EAGER)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "id_aluguel", required = true)
	private Aluguel aluguel;

	@Column(name = "vl_pagamento", precision = 14, scale = 2)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "vl_pagamento", precision = 14, scale = 2)
	private BigDecimal valorPagamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_pagamento")
	@br.com.anteros.persistence.metadata.annotation.Temporal(br.com.anteros.persistence.metadata.annotation.type.TemporalType.DATE_TIME)
	@br.com.anteros.persistence.metadata.annotation.Column(name = "dt_pagamento")
	private Date dataPagamento;

	public static Pagamento of(Cliente cliente, Funcionario funcionario, Aluguel aluguel, BigDecimal valorPagamento,
			Date dataPagamento) {
		Pagamento pagamento = new Pagamento();
		pagamento.setCliente(cliente);
		pagamento.setFuncionario(funcionario);
		pagamento.setAluguel(aluguel);
		pagamento.setValorPagamento(valorPagamento);
		pagamento.setDataPagamento(dataPagamento);
		return pagamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Aluguel getAluguel() {
		return aluguel;
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public BigDecimal getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
