package br.com.mkacunha.teste;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.modelo.Categoria;
import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Cliente;
import br.com.mkacunha.modelo.Estado;
import br.com.mkacunha.modelo.Filme;
import br.com.mkacunha.modelo.Idioma;
import br.com.mkacunha.modelo.Locadora;
import br.com.mkacunha.modelo.Pais;
import br.com.mkacunha.utils.HibernateUtil;

public class AppJPA {

	private EntityManager entityManager;

	public static void main(String[] args) {
		AppJPA app = new AppJPA();

		app.Ator();
		// app.categoria();
		// app.idioma();
		// app.pais();
		// app.filme();
		// app.estado();
		// app.cidade();
		// app.locadora();

		Cliente find = app.entityManager.find(Cliente.class, 4501l);

		app.entityManager.getTransaction().begin();
		app.entityManager.remove(find);
		app.entityManager.getTransaction().commit();

	}

	public AppJPA() {
		entityManager = HibernateUtil.createEntityManager();
	}

	public void Ator() {
		try {
			entityManager.getTransaction().begin();

			entityManager.persist(Ator.list(1000));

			entityManager.getTransaction().commit();

			List<Ator> resultList = null;

			resultList = entityManager.createQuery("select a from Ator a", Ator.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));

			resultList = entityManager.createQuery("select a from Ator a", Ator.class).getResultList();
			resultList.forEach(obj -> {
				obj.setDataUltimaAlteracao(new Date());
				try {
					entityManager.getTransaction().begin();
					entityManager.persist(obj);
					entityManager.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void categoria() {
		try {
			entityManager.getTransaction().begin();
			for (Categoria obj : Categoria.list()) {
				entityManager.persist(obj);
			}
			entityManager.getTransaction().commit();

			List<Categoria> resultList = null;

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select c from Categoria c", Categoria.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			entityManager.getTransaction().commit();

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select c from Categoria c", Categoria.class).getResultList();
			resultList.forEach(obj -> {
				obj.setDataUltimaAlteracao(new Date());
				try {
					entityManager.persist(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void idioma() {
		try {
			entityManager.getTransaction().begin();
			for (Idioma obj : Idioma.list()) {
				entityManager.persist(obj);
			}
			entityManager.getTransaction().commit();

			List<Idioma> resultList = null;

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select i from Idioma i", Idioma.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			entityManager.getTransaction().commit();

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select i from Idioma i", Idioma.class).getResultList();
			resultList.forEach(obj -> {
				obj.setDataUltimaAlteracao(new Date());
				try {
					entityManager.persist(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pais() {
		try {
			entityManager.getTransaction().begin();
			for (Pais obj : Pais.list()) {
				entityManager.persist(obj);
			}
			entityManager.getTransaction().commit();

			List<Pais> resultList = null;

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select p from Pais p", Pais.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void filme() {
		try {
			entityManager.getTransaction().begin();

			List<br.com.mkacunha.modelo.Ator> atores = entityManager.createQuery("select a from Ator a", Ator.class)
					.getResultList();
			List<Idioma> idiomas = entityManager.createQuery("select i from Idioma i", Idioma.class).getResultList();
			List<Categoria> categorias = entityManager.createQuery("select c from Categoria c", Categoria.class)
					.getResultList();

			// Filme.list().forEach(obj -> {
			// atores.forEach(a -> obj.add(a));
			// idiomas.forEach(i -> obj.add(i));
			// categorias.forEach(c -> obj.add(c));
			// try {
			// entityManager.persist(obj);
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// });

			entityManager.getTransaction().commit();

			List<Filme> resultList = null;

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select f from Filme f", Filme.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void estado() {
		try {
			entityManager.getTransaction().begin();
			Pais pais = entityManager.createQuery("select p from Pais p where p.nome = :nome", Pais.class)
					.setMaxResults(1).setParameter("nome", "BRASIL").getSingleResult();
			entityManager.getTransaction().commit();

			if (pais != null) {
				entityManager.getTransaction().begin();
				Estado.list(pais).forEach(obj -> {
					try {
						entityManager.persist(obj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				entityManager.getTransaction().commit();
			}

			entityManager.getTransaction().begin();
			entityManager.createQuery("select e from Estado e", Estado.class).getResultList()
					.forEach(e -> System.out.println(e));
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cidade() {
		try {
			entityManager.getTransaction().begin();
			Estado estado = entityManager.createQuery("select e from Estado e where e.nome = :nome", Estado.class)
					.setMaxResults(1).setParameter("nome", "Paraná").getSingleResult();
			entityManager.getTransaction().commit();

			if (estado != null) {
				entityManager.getTransaction().begin();
				Cidade.list(estado).forEach(obj -> {
					try {
						entityManager.persist(obj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				entityManager.getTransaction().commit();
			}

			entityManager.getTransaction().begin();
			entityManager.createQuery("select c from Cidade c", Cidade.class).getResultList()
					.forEach(cid -> System.out.println(cid));
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void locadora() {
		try {
			List<Cidade> cidades = entityManager.createQuery("select c from Cidade c", Cidade.class).getResultList();
			if (cidades != null) {
				entityManager.getTransaction().begin();

				cidades.forEach(c -> {
					try {
						// entityManager.persist(Locadora.of(c.getNome(),
						// Endereco.of(c), null));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

				entityManager.getTransaction().commit();
			}

			List<Locadora> resultList = null;

			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery("select l from Locadora l", Locadora.class).getResultList();
			resultList.forEach(obj -> {
				obj.getEndereco().setBairro("CENTRO DA CIDADE");
				obj.getEndereco().setComplemento(
						obj.getEndereco().getCidade().getEstado().getPais().toString() + " - OBS. PAIS ORIGEM");
				obj.getEndereco().setDataUltimaAlteracao(new Date());

				try {
					entityManager.persist(obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(obj);
			});
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
