package br.com.mkacunha.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class JPAPersistencia implements Persistencia {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public JPAPersistencia(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	@Override
	public void save(Object entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(List<?> entitys) {
		if (entitys != null && !entitys.isEmpty()) {
			try {
				entityManager.getTransaction().begin();
				entitys.forEach(entity -> {
					entityManager.persist(entity);
				});
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void remove(Object entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(List<?> entitys) {
		if (entitys != null && !entitys.isEmpty()) {
			try {
				entityManager.getTransaction().begin();
				entitys.forEach(entity -> entityManager.remove(entity));
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public <T> List<T> find(String query, Class<T> resultClass) {
		entityManager.close();
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager.createQuery(query, resultClass).getResultList();
	}

	@Override
	public <T> List<T> findAll(Class<T> resultClass) {
		StringBuilder query = new StringBuilder();
		query.append("select o from ");
		query.append(resultClass.getSimpleName());
		query.append(" o");
		return find(query.toString(), resultClass);
	}
	
	@Override
	public <T> List<T> executeQuery(Class<T> resultClass, String query, int maxResultList) {
		return entityManager.createQuery(query, resultClass).setMaxResults(maxResultList).getResultList();
	}

	@Override
	public <T> List<T> find(Class<T> resultClass, int maxResultList) {
		StringBuilder query = new StringBuilder();
		query.append("select o from ");
		query.append(resultClass.getSimpleName());
		query.append(" o ");
		return entityManager.createQuery(query.toString(), resultClass).setMaxResults(maxResultList).getResultList();
	}

	@Override
	public void removeAll(Class<?> clazz) {
		remove(findAll(clazz));
	}
	
	@Override
	public void clear() {
		entityManager.clear();
		entityManager.close();
		entityManager = entityManagerFactory.createEntityManager();	
	}
}
