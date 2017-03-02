package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.gerador.random.Random;
import br.com.mkacunha.modelo.Aluguel;
import br.com.mkacunha.modelo.Cliente;
import br.com.mkacunha.modelo.Filme;
import br.com.mkacunha.modelo.Funcionario;
import br.com.mkacunha.modelo.Locadora;

public class ExecucaoAluguel extends ExecucaoBase<Aluguel> {

	private List<Locadora> locadoras;
	private List<Filme> filmes;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private Random random = new Random();

	public ExecucaoAluguel(ExecucaoTeste execucao) {
		super(execucao);

		locadoras = persistencia.findAll(Locadora.class);
		filmes = persistencia.findAll(Filme.class);
		clientes = persistencia.findAll(Cliente.class);
		funcionarios = persistencia.findAll(Funcionario.class);
	}

	@Override
	public List<Aluguel> getObjetosPersistir() {
		return Aluguel.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE, locadoras, filmes, clientes, funcionarios);
	}

	@Override
	public void alterarDadosObjetos(List<Aluguel> objetos) {
		objetos.forEach(obj -> {
			obj.setDataRetorno(new Date());
			obj.setDataUltimaAlteracao(new Date());

			obj.getPagamentos().forEach(pag -> {
				pag.setDataPagamento(new Date());
				pag.setValorPagamento(random.nextMonetaryValue(10, 16));
			});
		});

	}

	@Override
	public void fimExecucaoTeste() {

	}
}
