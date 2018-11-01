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
		if (entity == null) {
			throw new Exception("Autor não encontrado!");
		}
		return entity;
	}
	
	public List<Autores> getAll() {
		return manager.createQuery("select e from Autores e", Autores.class).getResultList();
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
		if (!livrosService.getByIdAutor(id).isEmpty()) {
			throw new Exception("Este autor está sendo utilizado e não pode ser excluído!");
		}
		Autores entity = getById(id);
		manager.remove(entity);
	}

	private void validar(Autores entity) throws Exception {
		if (entity.getNome() == null || entity.getNome().isEmpty()) {
			throw new Exception("O nome deve ser informado!");
		}
	}

}