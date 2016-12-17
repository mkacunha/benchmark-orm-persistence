package br.com.mkacunha.execucao;

import java.util.List;

import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Cliente;
import br.com.mkacunha.modelo.Locadora;

public class ExecucaoCliente extends ExecucaoBase<Cliente> {

	private List<Locadora> locadoras;
	private List<Cidade> cidades;

	public ExecucaoCliente(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		locadoras = persistencia.findAll(Locadora.class);
		cidades = persistencia.findAll(Cidade.class);
	}

	@Override
	public List<Cliente> getObjetosPersistir() {
		return Cliente.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE, locadoras, cidades);
	}

	@Override
	public void alterarDadosObjetos(List<Cliente> objetos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Cliente.list(1000, locadoras, cidades));
	}

	@Override
	public void executarAposOperacaoPersistencia() {
		// TODO Auto-generated method stub

	}

}
