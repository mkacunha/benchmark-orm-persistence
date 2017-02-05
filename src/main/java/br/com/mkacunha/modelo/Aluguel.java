package br.com.mkacunha.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import br.com.mkacunha.gerador.random.Random;

@Entity
@Table(name = "aluguel")
public class Aluguel {

	@Id
	@Column(name = "id_aluguel")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_aluguel", nullable = false)	
	private Date data;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_inventario")
	private Inventario inventario;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_retorno")
	private Date dataRetorno;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcionario", nullable = false)
	private Funcionario funcionario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ultimaalteracao")
	private Date dataUltimaAlteracao;

	@OneToMany(mappedBy = "aluguel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Pagamento> pagamentos;

	public static Aluguel of(Date data, Inventario inventario, Cliente cliente, Date dataRetorno,
			Funcionario funcionario) {
		Aluguel aluguel = new Aluguel();
		aluguel.setData(data);
		aluguel.setInventario(inventario);
		aluguel.setCliente(cliente);
		aluguel.setDataRetorno(dataRetorno);
		aluguel.setFuncionario(funcionario);
		return aluguel;
	}

	public static List<Aluguel> list(int quantidade, List<Locadora> locadoras, List<Filme> filmes,
			List<Cliente> clientes, List<Funcionario> funcionarios) {
		List<Aluguel> alugueis = new ArrayList<>();

		Random random = new Random();

		for (int i = 0; i < quantidade; i++) {
			Locadora locadora = locadoras.get(random.nextInt(locadoras.size()));
			Filme filme = filmes.get(random.nextInt(filmes.size()));
			Cliente cliente = clientes.get(random.nextInt(clientes.size()));
			Funcionario funcionario = funcionarios.get(random.nextInt(funcionarios.size()));

			Inventario inventario = Inventario.of(filme, locadora);

			Aluguel aluguel = Aluguel.of(random.date(2015, 2016), inventario, cliente, null, funcionario);

			Pagamento pagamento1 = Pagamento.of(cliente, funcionario, aluguel, null, null);
			Pagamento pagamento2 = Pagamento.of(cliente, funcionario, aluguel, null, null);
			Pagamento pagamento3 = Pagamento.of(cliente, funcionario, aluguel, null, null);

			List<Pagamento> pagamentos = new ArrayList<>();
			pagamentos.add(pagamento1);
			pagamentos.add(pagamento2);
			pagamentos.add(pagamento3);

			aluguel.setPagamentos(pagamentos);

			alugueis.add(aluguel);
		}

		return alugueis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

}
