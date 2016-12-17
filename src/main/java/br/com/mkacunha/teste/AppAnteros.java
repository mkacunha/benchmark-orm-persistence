package br.com.mkacunha.teste;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import br.com.anteros.persistence.session.SQLSession;
import br.com.mkacunha.modelo.Ator;
import br.com.mkacunha.modelo.Categoria;
import br.com.mkacunha.modelo.Cidade;
import br.com.mkacunha.modelo.Estado;
import br.com.mkacunha.modelo.Filme;
import br.com.mkacunha.modelo.Idioma;
import br.com.mkacunha.modelo.Locadora;
import br.com.mkacunha.modelo.Pais;
import br.com.mkacunha.utils.AnterosUtil;

public class AppAnteros {

	public static void main(String[] args) {
		AppAnteros app = new AppAnteros();

		LocalDateTime inicio = LocalDateTime.now();
		// app.Ator();
		// app.categoria();
		// app.idioma();
		// app.pais();
		 app.filme();
		// app.estado();
		// app.cidade();
		// app.locadora();
		LocalDateTime fim = LocalDateTime.now();

		System.out.println(Duration.between(inicio, fim).getNano());
	}

	public AppAnteros() {
		AnterosUtil.initSessionFactory();
	}

	public void Ator() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			// for (Ator obj : Ator.list(10000)) {
			// session.save(obj);
			// }

			session.save(Ator.list(10));

			session.getTransaction().commit();

			List<Ator> resultList = null;

			session.getTransaction().begin();
			resultList = session.createQuery("select * from ator", Ator.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			session.getTransaction().commit();

			session.getTransaction().begin();
			resultList = session.createQuery("select * from ator", Ator.class).getResultList();
			resultList.forEach(obj -> {
				obj.setDataUltimaAlteracao(new Date());
				try {
					session.save(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void categoria() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			for (Categoria obj : Categoria.list()) {
				session.save(obj);
			}
			session.getTransaction().commit();

			List<Categoria> resultList = null;

			session.getTransaction().begin();
			resultList = session.createQuery("select * from categoria", Categoria.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			session.getTransaction().commit();

			session.getTransaction().begin();
			resultList = session.createQuery("select * from categoria", Categoria.class).getResultList();
			resultList.forEach(obj -> {
				obj.setDataUltimaAlteracao(new Date());
				try {
					session.save(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void idioma() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			for (Idioma obj : Idioma.list()) {
				session.save(obj);
			}
			session.getTransaction().commit();

			List<Idioma> resultList = null;

			session.getTransaction().begin();
			resultList = session.createQuery("select * from idioma", Idioma.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			session.getTransaction().commit();

			session.getTransaction().begin();
			resultList = session.createQuery("select * from idioma", Idioma.class).getResultList();
			resultList.forEach(obj -> {
				obj.setDataUltimaAlteracao(new Date());
				try {
					session.save(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pais() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			for (Pais obj : Pais.list()) {
				session.save(obj);
			}
			session.getTransaction().commit();

			List<Pais> resultList = null;

			session.getTransaction().begin();
			resultList = session.createQuery("select * from pais", Pais.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void filme() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();

			List<br.com.mkacunha.modelo.Ator> atores = session.createQuery("select * from ator", Ator.class)
					.getResultList();
			List<Idioma> idiomas = session.createQuery("select * from idioma", Idioma.class).getResultList();
			List<Categoria> categorias = session.createQuery("select * from categoria", Categoria.class)
					.getResultList();

			System.out.println(atores);
			System.out.println(idiomas);
			System.out.println(categorias);

			List<Filme> list = Filme.list(1, idiomas, atores, categorias);

			session.save(list.get(0));

			session.getTransaction().commit();

			List<Filme> resultList = null;

			session.getTransaction().begin();
			resultList = session.createQuery("select * from filme", Filme.class).getResultList();
			resultList.forEach(obj -> System.out.println(obj));
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void estado() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			Pais pais = session.createQuery("select * from pais where ds_nome = 'BRASIL' limit 1", Pais.class)
					.getSingleResult();
			session.getTransaction().commit();

			if (pais != null) {
				session.getTransaction().begin();
				Estado.list(pais).forEach(obj -> {
					try {
						session.save(obj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				session.getTransaction().commit();
			}

			session.getTransaction().begin();
			session.createQuery("select * from estado", Estado.class).getResultList()
					.forEach(e -> System.out.println(e));
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cidade() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			Estado estado = session.createQuery("select * from estado where ds_nome = 'Paraná' LIMIT 1", Estado.class)
					.getSingleResult();
			session.getTransaction().commit();

			if (estado != null) {
				session.getTransaction().begin();
				Cidade.list(estado).forEach(obj -> {
					try {
						session.save(obj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				session.getTransaction().commit();
			}

			session.getTransaction().begin();
			session.createQuery("select * from cidade", Cidade.class).getResultList()
					.forEach(cid -> System.out.println(cid));
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void locadora() {
		try {
			SQLSession session = AnterosUtil.getSession();

			session.getTransaction().begin();
			List<Cidade> cidades = session.createQuery("select * from cidade", Cidade.class).getResultList();
			if (cidades != null) {

				cidades.forEach(c -> {
					try {
						// session.save(Locadora.of(c.getNome(), Endereco.of(c),
						// null));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
			session.getTransaction().commit();
			List<Locadora> resultList = null;

			session.getTransaction().begin();
			resultList = session.createQuery("select * from locadora", Locadora.class).getResultList();
			resultList.forEach(obj -> {
				obj.getEndereco().setBairro("CENTRO DA CIDADE");
				obj.getEndereco().setComplemento(
						obj.getEndereco().getCidade().getEstado().getPais().toString() + " - OBS. PAIS ORIGEM");
				obj.getEndereco().setDataUltimaAlteracao(new Date());

				try {
					session.save(obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(obj);
			});
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
