package org.bairro.biblioteca.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.bairro.biblioteca.entidades.Emprestimos;
import org.bairro.biblioteca.entidades.Livros;

@Stateless
public class LivrosService {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	@Inject
	private EmprestimosService emprestimoService;

	public Livros getById(Integer id) throws Exception {
		Livros entity = manager.find(Livros.class, id);
		// Caso o livro buscado pelo ID não exista uma exceção será disparada
		if (entity == null) {
			throw new Exception("Livro não encontrado!");
		}
		return entity;
	}
	
	public List<Livros> getAll() {
		return manager.createQuery("select e from Livros e", Livros.class).getResultList();
	}
	
	public Livros salvar(Livros entity) throws Exception {
		validar(entity);
		manager.persist(entity);
		return entity;
	}

	public Livros atualizar(Livros entity) throws Exception {
		validar(entity);
		manager.merge(entity);
		return entity;
	}
	
	public void deletar(Integer id) throws Exception {
		// Verificando se o livro a ser excluído possui referência na tabela de empréstimo 
		// pois caso exista um empréstimo para o livro este não pode ser excluído.
		List<Emprestimos> listaDeEmprestimosDoLivro = emprestimoService.getByIdLivro(id);
		if (listaDeEmprestimosDoLivro != null && !listaDeEmprestimosDoLivro.isEmpty()) {
			throw new Exception("O livro não pode ser deletado pois existe(m) registro(s) de empréstimo(s) dele!");
		}
		// Efetua uma busca em livros pelo ID onde caso não encontrado será disparado uma exceção.
		Livros entity = getById(id);
		manager.remove(entity);
	}

	/**
	 * Médodo para validar o livro antes de inserir ou atualizar no banco.
	 * @param entity de livro
	 * @throws Exception
	 */
	private void validar(Livros entity) throws Exception {
		if (entity.getTitulo() == null || entity.getTitulo().isEmpty()) {
			throw new Exception("O título deve ser informado!");
		}
	}

	/**
	 * Método para buscar livro(s) pelo ID do autor
	 * @param idAutor
	 * @return
	 */
	public List<Livros> getByIdAutor(Integer idAutor) {
		TypedQuery<Livros> query = manager.createQuery("select e from Livros e where e.autor.idAutor = :idAutor", Livros.class);
		query.setParameter("idAutor", idAutor);
		return query.getResultList();
	}
	
	/**
	 * Método para buscar livro(s) pelo ID da editora
	 * @param idEditora
	 */
	public List<Livros> getByIdEditora(Integer idEditora) throws Exception {
		return manager.createQuery("select l from Livros l where l.editora.idEditora = :idEditora", Livros.class)
					  .setParameter("idEditora", idEditora)
					  .getResultList();
	}

	/**
	 * Método para buscar livro(s) pelo título
	 * @param titulo
	 */
	public List<Livros> getByTituto(String titulo) throws Exception {
		List<Livros> listaByTitulo = manager
				.createQuery("select l from Livros l where UPPER(l.titulo) LIKE :titulo ", Livros.class)
				.setParameter("titulo", "%" + titulo.toUpperCase() + "%")
				.getResultList();
		if (listaByTitulo == null || listaByTitulo.isEmpty()) {
			throw new Exception("Nenhum livro encontrado com este título!");
		}
		return listaByTitulo;
	}
	
	/**
	 * Método para buscar livro(s) pela descrição
	 * @param descricao
	 */
	public List<Livros> getByDescricao(String descricao) throws Exception {
		List<Livros> listaByTitulo = manager
				.createQuery("select l from Livros l where UPPER(l.descricao) LIKE :desc", Livros.class)
				.setParameter("desc", "%" + descricao.toUpperCase() + "%")
				.getResultList();
		if (listaByTitulo == null || listaByTitulo.isEmpty()) {
			throw new Exception("Nenhum livro encontrado com esse título!");
		}
		return listaByTitulo;
	}

}