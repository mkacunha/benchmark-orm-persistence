package br.com.mkacunha.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EclipseLinkUtil {

	private static final String PERSISTENCE_UNIT_NAME = "benchmark-unit-eclipselink";

	private static EntityManagerFactory entityManagerFactory;

	public synchronized static EntityManager createEntityManager() {
		initEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		return entityManager;
	}

	public synchronized static EntityManagerFactory getEntityManagerFactory() {
		initEntityManagerFactory();
		return entityManagerFactory;
	}

	public synchronized static void initEntityManagerFactory() {
		if (entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

}
