package org.bairro.biblioteca.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.bairro.biblioteca.entidades.Clientes;
import org.bairro.biblioteca.entidades.Emprestimos;

@Stateless
public class EmprestimosService {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	@Inject
	private ClientesService clienteService;

	public Emprestimos getById(Integer id) throws Exception {
		Emprestimos entity = manager.find(Emprestimos.class, id);
		// Caso o empréstimo buscado pelo ID não exista uma exceção será disparada
		if (entity == null) {
			throw new Exception("Empréstimo não encontrado!");
		}
		return entity;
	}
	
	public List<Emprestimos> getAll() {
		return manager.createQuery("select e from Emprestimos e", Emprestimos.class).getResultList();
	}
	
	public Emprestimos salvar(Emprestimos entity) throws Exception {
		validar(entity);
		manager.persist(entity);
		return entity;
	}

	public Emprestimos atualizar(Emprestimos entity) throws Exception {
		validar(entity);
		manager.merge(entity);
		return entity;
	}
	
	public void deletar(Integer id) throws Exception {
		// Efetua uma busca em emprestimos pelo ID onde caso não encontrado será disparado uma exceção.
		Emprestimos entity = getById(id);
		manager.remove(entity);
	}

	/**
	 * Método para buscar emprestimo(s) pelo ID do cliente
	 * @param idCliente
	 * @return
	 */
	public List<Emprestimos> getByIdCliente(Integer idCliente) {
		TypedQuery<Emprestimos> query = manager.createQuery("select e from Emprestimos e where e.cliente.idCliente = :id", Emprestimos.class);
		query.setParameter("id", idCliente);
		return query.getResultList();
	}

	/**
	 * Método para buscar emprestimo(s) pelo ID do livro
	 * @param idLivro
	 * @return
	 */
	public List<Emprestimos> getByIdLivro(Integer idLivro) throws Exception {
		return manager.createQuery("select e from Emprestimos e inner join fetch e.itens item where item.livro.idLivro = :id", Emprestimos.class)
					  .setParameter("id", idLivro)
					  .getResultList();
	}
	
	/**
	 * Médodo para validar o emprestimo antes de inserir ou atualizar no banco.
	 * @param entity de emprestimo
	 * @throws Exception
	 */
	private void validar(Emprestimos entity) throws Exception {
		if (entity.getDataEmprestimo() == null) {
			throw new Exception("A data do empréstimo deve ser informada!");
		}
		if (entity.getCliente() == null || entity.getCliente().getIdCliente() == null) {
			throw new Exception("O cliente deve ser informado!");
		}
		Clientes cliente = clienteService.getById(entity.getCliente().getIdCliente());
		if (cliente.getAtivo().compareTo(0) == 0) {
			throw new Exception("O cliente informado não está ativo!");
		}
		if (entity.getItens() == null || entity.getItens().isEmpty()) {
			throw new Exception("Pelo menos um livro deve ser informado!");
		}
	}

}