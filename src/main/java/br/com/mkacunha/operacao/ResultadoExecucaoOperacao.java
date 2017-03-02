package br.com.mkacunha.operacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.mkacunha.execucao.ExecucaoTeste;

public class ResultadoExecucaoOperacao {

	private final int CONVERSAO_MILISEGUNDO = 1000;

	private TipoOperacao tipoOperacao;
	private Long tempoExecucao;
	private Integer quantidadeRegistro;

	private LocalDateTime inicioExecucao;
	private LocalDateTime fimExecucao;
	private boolean isExecucaoIniciada = false;
	private boolean isOperacaoExecutada = false;

	Logger logger = Logger.getLogger(ExecucaoTeste.LOG_EXECUCAO);

	public ResultadoExecucaoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public void iniciarExecucao() {
		if (isExecucaoIniciada)
			throw new RuntimeException("Execução da operação " + this.tipoOperacao + " já iniciada");

		if (isOperacaoExecutada)
			throw new RuntimeException("Operação " + this.tipoOperacao + " já executada");

		logger.info(" --> Iniciou operacao " + this.tipoOperacao);

		isExecucaoIniciada = true;
		inicioExecucao = LocalDateTime.now();
	}

	public void finalizarExecucao() {
		if (!isExecucaoIniciada)
			throw new RuntimeException("Não possui execução em andamento para ser encerrada");

		fimExecucao = LocalDateTime.now();

		tempoExecucao = Duration.between(inicioExecucao, fimExecucao).toMillis();
		isOperacaoExecutada = true;
		logger.info(" --> Finalizou operacao " + this.tipoOperacao + " " + this.quantidadeRegistro + " registro(s): "
				+ converteMiliSegundoParaSegundo(tempoExecucao) + " segundo(s)");
	}

	public Long getMiliSegundoTempoExecucao() {
		return tempoExecucao;
	}

	private Double converteMiliSegundoParaSegundo(Long miliSegundos) {
		if (miliSegundos == null || miliSegundos == 0)
			return 0.0;

		return BigDecimal.valueOf(miliSegundos)
				.divide(BigDecimal.valueOf(CONVERSAO_MILISEGUNDO), 3, RoundingMode.HALF_DOWN).doubleValue();
	}

	public Double getSegundoTempoExecucao() {
		return converteMiliSegundoParaSegundo(tempoExecucao);
	}

	public void setQuantidadeRegistro(Integer quantidadeRegistro) {
		this.quantidadeRegistro = quantidadeRegistro;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(tipoOperacao);
		sb.append(": ");
		sb.append(this.getSegundoTempoExecucao());
		return sb.toString();
	}
}
