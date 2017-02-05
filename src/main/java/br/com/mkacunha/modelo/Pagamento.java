package br.com.mkacunha.modelo;

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

@Entity
@Table(name = "pagamento")
public class Pagamento {

	@Id
	@Column(name = "id_pagamento")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcionario", nullable = false)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_aluguel", nullable = false)
	private Aluguel aluguel;

	@Column(name = "vl_pagamento", precision = 14, scale = 2)
	private BigDecimal valorPagamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_pagamento")
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
