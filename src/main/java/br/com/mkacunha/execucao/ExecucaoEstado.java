package br.com.mkacunha.execucao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mkacunha.gerador.random.GeradorTexto;
import br.com.mkacunha.modelo.Estado;
import br.com.mkacunha.modelo.Pais;

public class ExecucaoEstado extends ExecucaoBase<Estado> {

	private List<Pais> paises;

	public ExecucaoEstado(ExecucaoTeste execucao, int quantidade) {
		super(execucao, quantidade);
		
		Pais pais = Pais.of("BRASIL", "BR", "BRASIL");
		
		persistencia.save(pais);

		paises = new ArrayList<>();
		
		paises.add(pais);
	}

	@Override
	public List<Estado> getObjetosPersistir() {
		return Estado.list(paises, ExecucaoTeste.QUANTIDA_REGISTROS_BASE_TESTE);
	}

	@Override
	public void alterarDadosObjetos(List<Estado> objetos) {
		GeradorTexto geradorTexto = new GeradorTexto();
		objetos.forEach(estado -> {
			estado.setDataUltimaAlteracao(new Date());
			estado.setDescricao(geradorTexto.get(600, 800));
		});
	}

	@Override
	public void executarAposOperacaoPersistencia() {

	}

	@Override
	public void fimExecucaoTeste() {
		persistencia.save(getObjetosPersistir());
	}
}
