package br.com.mkacunha.execucao;

import java.util.Scanner;

import br.com.mkacunha.persistencia.HibernatePersistencia;

public class ExecucaoTesteHibernate {

	public static void main(String[] args) {
		System.out.printf("Número execução:");
		new ExecucaoTeste(new HibernatePersistencia(), "Hibernate", new Scanner(System.in).nextInt()).executar();
	}
}
