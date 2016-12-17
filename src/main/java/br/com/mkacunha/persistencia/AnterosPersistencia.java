package br.com.mkacunha.persistencia;

import java.util.List;

import br.com.anteros.persistence.session.SQLSession;
import br.com.mkacunha.modelo.Aluguel;
import br.com.mkacunha.modelo.Cliente;
import br.com.mkacunha.modelo.Endereco;
import br.com.mkacunha.modelo.Funcionario;
import br.com.mkacunha.modelo.Inventario;
import br.com.mkacunha.modelo.Locadora;
import br.com.mkacunha.utils.AnterosUtil;

public class AnterosPersistencia implements Persistencia {

	private SQLSession session;

	public AnterosPersistencia() {
		session = AnterosUtil.getSession();
	}

	@Override
	public void save(Object entity) {
		try {
			begin();
			session.save(entity);
			commit();
		} catch (Exception e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(List<?> entitys) {
		try {
			begin();
			entitys.forEach(entity -> {
				try {
					session.save(entity);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			commit();
		} catch (Exception e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(Object entity) {
		try {
			begin();
			removeWithoutTransaction(entity);
			commit();
		} catch (Exception e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(List<?> entitys) {
		try {
			begin();
			entitys.forEach(entity -> {
				try {
					removeWithoutTransaction(entity);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			commit();
		} catch (Exception e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	private void removeWithoutTransaction(Object entity) {
		try {
			Endereco endereco = null;
			Inventario inventario = null;

			if (entity instanceof Cliente)
				endereco = ((Cliente) entity).getEndereco();
			else if (entity instanceof Locadora)
				endereco = ((Locadora) entity).getEndereco();
			else if (entity instanceof Funcionario)
				endereco = ((Funcionario) entity).getEndereco();
			else if (entity instanceof Aluguel)
				inventario = ((Aluguel) entity).getInventario();

			session.remove(entity);

			if (endereco != null)
				session.remove(endereco);

			if (inventario != null)
				session.remove(inventario);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> find(String query, Class<T> resultClass) {
		try {
			session.close();
			session = AnterosUtil.getSession();
			begin();
			List<T> resultList = session.createQuery(query, resultClass).getResultList();
			commit();
			return resultList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> findAll(Class<T> resultClass) {
		StringBuilder query = new StringBuilder();
		query.append("select * from ");
		query.append(resultClass.getSimpleName());
		return find(query.toString(), resultClass);
	}

	@Override
	public <T> List<T> find(Class<T> resultClass, int maxResultList) {
		StringBuilder query = new StringBuilder();
		query.append("select * from ");
		query.append(resultClass.getSimpleName());
		query.append(" limit ");
		query.append(maxResultList);
		return find(query.toString(), resultClass);
	}

	@Override
	public void removeAll(Class<?> clazz) {
		try {
			remove(findAll(clazz));
		} catch (Exception e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	private void begin() {
		try {
			session.getTransaction().begin();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void commit() {
		try {
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void rollback() {
		try {
			if (session.getTransaction().isActive())
				session.getTransaction().rollback();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
