package br.com.mkacunha.execucao;

import br.com.mkacunha.persistencia.EclipseLinkPersistencia;

public class ExecucaoTesteEclipseLink {

	public static void main(String[] args) {
		System.out.printf("Número execução:");
		new ExecucaoTeste(new EclipseLinkPersistencia(), "Hibernate", 1).executar();
	}
}
