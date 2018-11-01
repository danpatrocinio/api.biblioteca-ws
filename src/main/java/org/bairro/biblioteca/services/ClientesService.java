package org.bairro.biblioteca.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bairro.biblioteca.entidades.Clientes;

@Stateless
public class ClientesService {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	public Clientes getById(Integer id) throws Exception {
		Clientes entity = manager.find(Clientes.class, id);
		if (entity == null) {
			throw new Exception("Cliente não encontrado!");
		}
		return entity;
	}
	
	public List<Clientes> getAll() {
		return manager.createQuery("select e from Clientes e", Clientes.class).getResultList();
	}
	
	public Clientes salvar(Clientes entity) throws Exception {
		validar(entity);
		manager.persist(entity);
		return entity;
	}

	public Clientes atualizar(Clientes entity) throws Exception {
		validar(entity);
		manager.merge(entity);
		return entity;
	}
	
	public void deletar(Integer id) throws Exception {
		Clientes entity = getById(id);
		manager.remove(entity);
	}

	private void validar(Clientes entity) throws Exception {
		if (entity.getCpf() == null || entity.getCpf().isEmpty() || entity.getCpf().length() != 11) {
			throw new Exception("Um CPF válido deve ser informado!");
		}
		if (entity.getNome() == null || entity.getNome().isEmpty() || entity.getNome().length() > 50) {
			throw new Exception("O nome deve ser informado!");
		}
		if (entity.getEndereco() == null || entity.getEndereco().isEmpty() || entity.getEndereco().length() > 50) {
			throw new Exception("O endereço informado é invalido!");
		}
		if (entity.getTelefone() == null || entity.getTelefone().isEmpty() || entity.getTelefone().length() > 12) {
			throw new Exception("Deve ser informado um telefone válido!");
		}
		if (entity.getAtivo() == null || entity.getAtivo().compareTo(1) > 0 || entity.getAtivo().compareTo(0) < 0) {
			throw new Exception("Informe se o cliente está ativo! 0 - Inativo ou 1 - Ativo");
		}
	}

}