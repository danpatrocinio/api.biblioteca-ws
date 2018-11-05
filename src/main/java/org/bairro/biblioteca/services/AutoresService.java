package org.bairro.biblioteca.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bairro.biblioteca.entidades.Autores;

@Stateless
public class AutoresService {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	@Inject
	private LivrosService livrosService;

	public Autores getById(Integer id) throws Exception {
		Autores entity = manager.find(Autores.class, id);
		// Caso o autor buscado pelo ID não exista uma exceção será disparada
		if (entity == null) {
			throw new Exception("Autor não encontrado!");
		}
		return entity;
	}
	
	public List<Autores> getAll() {
		return manager.createQuery("select e from Autores e", Autores.class).getResultList();
	}
	
	/**
	 * Método para buscar autor(es) pelo nome
	 * @param nome
	 */
	public List<Autores> getByNome(String nome) throws Exception {
		List<Autores> listaByNome = manager
				.createQuery("select a from Autores a where UPPER(a.nome) LIKE :nome ", Autores.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getResultList();
		if (listaByNome == null || listaByNome.isEmpty()) {
			throw new Exception("Nenhum autor encontrado com este nome!");
		}
		return listaByNome;
	}
	
	public Autores salvar(Autores entity) throws Exception {
		validar(entity);
		manager.persist(entity);
		return entity;
	}

	public Autores atualizar(Autores entity) throws Exception {
		validar(entity);
		manager.merge(entity);
		return entity;
	}
	
	public void deletar(Integer id) throws Exception {
		// Verificando se o autor a ser excluído possui referência na tabela de livros 
		// pois caso exista um livro para o autor este não pode ser excluído.
		if (!livrosService.getByIdAutor(id).isEmpty()) {
			throw new Exception("O autor não pode ser excluído pois existe(m) registro(s) de livro(s) dele!");
		}		
		// Efetua uma busca em autores pelo ID onde caso não encontrado será disparado uma exceção.
		Autores entity = getById(id);
		manager.remove(entity);
	}

	/**
	 * Médodo para validar o autor antes de inserir ou atualizar no banco.
	 * @param entity de autor
	 * @throws Exception
	 */
	private void validar(Autores entity) throws Exception {
		if (entity.getNome() == null || entity.getNome().isEmpty()) {
			throw new Exception("O nome deve ser informado!");
		}
	}

}