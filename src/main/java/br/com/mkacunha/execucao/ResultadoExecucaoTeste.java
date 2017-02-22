package br.com.mkacunha.execucao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ResultadoExecucaoTeste {

	private final int CONVERSAO_MILISEGUNDO = 1000;

	private String descricao;
	private Integer execucao = 0;
	private Long tempoPrimeiraExecucao;
	private Long tempoUltimaExecucao;
	private Integer quantidadeRegistro;

	private List<Long> execucoes = new ArrayList<>();

	private LocalDateTime inicioExecucao;
	private LocalDateTime fimExecucao;
	private boolean isExecucaoIniciada;

	Logger logger = Logger.getLogger(ExecucaoTeste.LOG_EXECUCAO);

	public ResultadoExecucaoTeste(String descricao) {
		this.descricao = descricao;
	}

	public void iniciarExecucao() {
		if (isExecucaoIniciada)
			throw new RuntimeException("Execução de número " + execucao + " já iniciada");

		logger.info("Iniciou operacao " + this.descricao);

		isExecucaoIniciada = true;
		execucao++;
		inicioExecucao = LocalDateTime.now();
	}

	public void finalizarExecucao() {
		if (!isExecucaoIniciada)
			throw new RuntimeException("Não possui execução em andamento para ser encerrada");

		fimExecucao = LocalDateTime.now();

		long duracao = Duration.between(inicioExecucao, fimExecucao).toMillis();

		if (execucao == 1)
			tempoPrimeiraExecucao = duracao;

		tempoUltimaExecucao = duracao;

		execucoes.add(duracao);

		inicioExecucao = null;
		fimExecucao = null;
		isExecucaoIniciada = false;

		logger.info("Finalizou operacao " + this.descricao + ": " + converteMiliSegundoParaSegundo(duracao)
				+ " segundo(s)");
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getMiliSegundoPrimeiraExecucao() {
		return tempoPrimeiraExecucao;
	}

	private Double converteMiliSegundoParaSegundo(Long miliSegundos) {
		if (miliSegundos == null || miliSegundos == 0)
			return 0.0;

		return BigDecimal.valueOf(miliSegundos)
				.divide(BigDecimal.valueOf(CONVERSAO_MILISEGUNDO), 3, RoundingMode.HALF_DOWN).doubleValue();
	}

	private Double converteMiliSegundoParaSegundo(Double miliSegundos) {
		if (miliSegundos == null || miliSegundos == 0.0)
			return 0.0;

		return BigDecimal.valueOf(miliSegundos)
				.divide(BigDecimal.valueOf(CONVERSAO_MILISEGUNDO), 3, RoundingMode.HALF_DOWN).doubleValue();
	}

	public Double getSegundoPrimeiraExecucao() {
		return converteMiliSegundoParaSegundo(tempoPrimeiraExecucao);
	}

	public Long getMiliSegundoUltimaExecucao() {
		return tempoUltimaExecucao;
	}

	public Double getSegundoUltimaExecucao() {
		return converteMiliSegundoParaSegundo(tempoUltimaExecucao);
	}

	public Double getMiliSegundoMediaExecucao() {
		if (!execucoes.isEmpty()) {
			Long soma = 0l;
			for (Long tempo : execucoes)
				soma += tempo;
			return BigDecimal.valueOf(soma).divide(BigDecimal.valueOf(execucoes.size()), 3, RoundingMode.HALF_DOWN)
					.doubleValue();
		}
		return 0.0;
	}

	public Double getSegundoMediaExecucao() {
		Double miliSegundos = getMiliSegundoMediaExecucao();

		if (miliSegundos.compareTo(0.0) > 0) {
			return converteMiliSegundoParaSegundo(miliSegundos);
		}

		return 0.0;
	}

	public void setQuantidadeRegistro(Integer quantidadeRegistro) {
		this.quantidadeRegistro = quantidadeRegistro;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(descricao);
		sb.append(": ");
		sb.append(this.getSegundoPrimeiraExecucao());

		return sb.toString();
	}
}
