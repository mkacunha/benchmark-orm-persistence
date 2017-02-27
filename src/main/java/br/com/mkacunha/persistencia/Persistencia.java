package br.com.mkacunha.persistencia;

import java.util.List;

public interface Persistencia {

	public void save(Object entity);

	public void save(List<?> entitys);

	public void remove(Object entity);

	public void remove(List<?> entitys);

	public <T> List<T> find(String query, Class<T> resultClass);

	public <T> List<T> findAll(Class<T> resultClass);

	public <T> List<T> find(Class<T> resultClass, int maxResultList);

	public <T> List<T> executeQuery(Class<T> resultClass, String query, int maxResultList);
	
	public void removeAll(Class<?> clazz);
}
