package br.com.mkacunha.operacao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoExecucao {

	private Map<String, List<ResultadoExecucaoOperacao>> resultados = new HashMap<>();

	public ResultadoExecucaoOperacao add(Class<?> clazz, TipoOperacao operacao) {
		List<ResultadoExecucaoOperacao> execucoes;

		if (resultados.containsKey(clazz.getSimpleName())) {
			execucoes = resultados.get(clazz.getSimpleName());
		} else {
			execucoes = new ArrayList<>();
			resultados.put(clazz.getSimpleName(), execucoes);
		}

		ResultadoExecucaoOperacao resultadoExecucao = new ResultadoExecucaoOperacao(operacao);
		execucoes.add(resultadoExecucao);
		return resultadoExecucao;
	}
	
	public void imprimirResultado() {
		resultados.forEach((name, execucoes) -> {
			String imprimir = name + ":";
			
			for (ResultadoExecucaoOperacao o : execucoes)
				imprimir += o.getSegundoTempoExecucao() + "	";
			
			System.out.println(imprimir);
		});
	}
	
	public void salvarArquivo(String framework, int numeroExecucao){		
		try {
			FileWriter file = new FileWriter("log" + framework + "_" + numeroExecucao + ".txt", true);
			
			resultados.forEach((name, execucoes) -> {
				String imprimir = "\n" + name + ":";
				
				for (ResultadoExecucaoOperacao o : execucoes)
					imprimir += o.getSegundoTempoExecucao() + "	";
				
				try {
					file.write(imprimir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});			

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
