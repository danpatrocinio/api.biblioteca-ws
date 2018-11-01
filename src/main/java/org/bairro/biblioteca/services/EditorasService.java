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
		if (entity == null) {
			throw new Exception("Editora não encontrada!");
		}
		return entity;
	}
	
	public List<Editoras> getAll() {
		return manager.createQuery("select e from Editoras e", Editoras.class).getResultList();
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
		if (!livrosService.getByIdEditora(id).isEmpty()) {
			throw new Exception("Esta editora está sendo utilizada e não pode ser excluida!");
		}
		Editoras entity = getById(id);
		manager.remove(entity);
	}

	private void validar(Editoras entity) throws Exception {
		if (entity.getNome() == null || entity.getNome().isEmpty()) {
			throw new Exception("O nome deve ser informado!");
		}
	}

}