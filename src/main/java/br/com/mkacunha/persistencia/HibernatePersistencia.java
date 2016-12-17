package br.com.mkacunha.persistencia;

import br.com.mkacunha.utils.HibernateUtil;

public class HibernatePersistencia extends JPAPersistencia {

	public HibernatePersistencia() {
		super(HibernateUtil.getEntityManagerFactory());
	}
}
