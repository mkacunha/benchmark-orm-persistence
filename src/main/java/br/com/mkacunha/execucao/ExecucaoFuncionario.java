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
	private ResultadoExecucaoTeste alterarFuncionarioEndereco;

	public ExecucaoFuncionario(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);

		cidades = persistencia.find(Cidade.class, 1000);
		locadoras = persistencia.find(Locadora.class, 1000);

		StringBuilder sb = new StringBuilder();
		sb.append("Alterar por ");
		sb.append(quantidade);
		sb.append(
				" vez(es) atributos de objetos referentes a classe Funcionario e atributos referente a classe Endereco");
		alterarFuncionarioEndereco = execucao.add(sb.toString());

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
		List<Funcionario> objetos = persistencia.findAll(Funcionario.class);

		objetos.forEach(obj -> {
			obj.setUsuario(geradorTexto.get(8, 10).replace(" ", ""));
			obj.setDataUltimaAlteracao(new Date());
			obj.getEndereco().setDataUltimaAlteracao(new Date());
			obj.getEndereco().setComplemento(geradorTexto.get(100, 150));
		});

		alterarFuncionarioEndereco.setQuantidadeRegistro(objetos.size());
		alterarFuncionarioEndereco.iniciarExecucao();
		persistencia.save(objetos);
		alterarFuncionarioEndereco.finalizarExecucao();
	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(Funcionario.list(1000, locadoras, cidades));
	}

}
