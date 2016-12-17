package br.com.mkacunha.utils;

import br.com.anteros.persistence.session.SQLSession;
import br.com.anteros.persistence.session.SQLSessionFactory;
import br.com.anteros.persistence.session.configuration.AnterosPersistenceConfiguration;

public class AnterosUtil {

	private static SQLSessionFactory sessionFactory;

	private AnterosUtil() {

	}

	public synchronized static SQLSession getSession() {
		if (sessionFactory == null)
			initSessionFactory();

		try {
			return sessionFactory.getCurrentSession();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized static SQLSessionFactory getSessionFactory() {
		initSessionFactory();
		return sessionFactory;
	}

	public synchronized static void initSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new AnterosPersistenceConfiguration().configure("META-INF/anteros-persistence.xml")
						.buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
