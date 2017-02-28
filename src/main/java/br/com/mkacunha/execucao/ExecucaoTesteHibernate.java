package br.com.mkacunha.execucao;

import br.com.mkacunha.persistencia.HibernatePersistencia;

public class ExecucaoTesteHibernate {

	public static void main(String[] args) {
		System.out.printf("Número execução:");
		new ExecucaoTeste(new HibernatePersistencia(), "Hibernate", 1).executar();
	}
}
