package org.bairro.biblioteca.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bairro.biblioteca.entidades.Editoras;

@Stateless
public class EditorasService {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	@Inject
	private LivrosService livrosService;

	public Editoras getById(Integer id) throws Exception {
		Editoras entity = manager.find(Editoras.class, id);
		// Caso a editora buscada pelo ID não exista uma exceção será disparada
		if (entity == null) {
			throw new Exception("Editora não encontrada!");
		}
		return entity;
	}
	
	public List<Editoras> getAll() {
		return manager.createQuery("select e from Editoras e", Editoras.class).getResultList();
	}
	
	/**
	 * Método para buscar editora(s) pelo nome
	 * @param nome
	 */
	public List<Editoras> getByNome(String nome) throws Exception {
		List<Editoras> listaByNome = manager
				.createQuery("select e from Editoras e where UPPER(e.nome) LIKE :nome ", Editoras.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getResultList();
		if (listaByNome == null || listaByNome.isEmpty()) {
			throw new Exception("Nenhum editora encontrada com este nome!");
		}
		return listaByNome;
	}
	
	public Editoras salvar(Editoras entity) throws Exception {
		validar(entity);
		manager.persist(entity);
		return entity;
	}

	public Editoras atualizar(Editoras entity) throws Exception {
		validar(entity);
		manager.merge(entity);
		return entity;
	}
	
	public void deletar(Integer id) throws Exception {
		// Verificando se a editora a ser excluída possui referência na tabela de livros 
		// pois caso exista um livro para o autor este não pode ser excluído.
		if (!livrosService.getByIdEditora(id).isEmpty()) {
			throw new Exception("A editora não pode ser excluída pois existe(m) registro(s) de livro(s) com ela!");
		}
		// Efetua uma busca em editoras pelo ID onde caso não encontrado será disparado uma exceção.
		Editoras entity = getById(id);
		manager.remove(entity);
	}

	/**
	 * Médodo para validar a editora antes de inserir ou atualizar no banco.
	 * @param entity de editora
	 * @throws Exception
	 */
	private void validar(Editoras entity) throws Exception {
		if (entity.getNome() == null || entity.getNome().isEmpty()) {
			throw new Exception("O nome deve ser informado!");
		}
	}

}