package br.com.mkacunha.execucao;

import java.util.Date;
import java.util.List;

import br.com.mkacunha.gerador.random.GeradorTexto;
import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Funcionario;
import br.com.mkacunha.modelo.Locadora;

public class ExecucaoFuncionario extends ExecucaoBase<Funcionario> {

	private List<Cidade> cidades;
	private List<Locadora> locadoras;
	private GeradorTexto geradorTexto = new GeradorTexto();

	public ExecucaoFuncionario(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		cidades = persistencia.find(Cidade.class, 1000);
		locadoras = persistencia.find(Locadora.class, 1000);
	}

	@Override
	public List<Funcionario> getObjetosPersistir() {
		return Funcionario.list(ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE, locadoras, cidades);
	}

	@Override
	public void alterarDadosObjetos(List<Funcionario> objetos) {
		objetos.forEach(obj -> {
			obj.setDataUltimaAlteracao(new Date());
			obj.setUsuario(geradorTexto.get(5, 8).replace(" ", ""));
		});

	}

	@Override
	public void executarAposOperacaoPersistencia() {
		
	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Funcionario.list(1000, locadoras, cidades));
	}

}
