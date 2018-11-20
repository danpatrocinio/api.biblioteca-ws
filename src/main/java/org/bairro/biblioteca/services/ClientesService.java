package org.bairro.biblioteca.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bairro.biblioteca.entidades.Clientes;
import org.bairro.biblioteca.entidades.Emprestimos;

@Stateless
public class ClientesService {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@Inject
	private EmprestimosService emprestimoService;
	
	public Clientes getById(Integer id) throws Exception {
		Clientes entity = manager.find(Clientes.class, id);
		// Caso o cliente buscado pelo ID não exista uma exceção será disparada
		if (entity == null) {
			throw new Exception("Cliente não encontrado!");
		}
		return entity;
	}
	
	/**
	 * Método para buscar cliente(s) pelo nome
	 * @param nome
	 */
	public List<Clientes> getByNome(String nome) throws Exception {
		List<Clientes> listaByNome = manager
				.createQuery("select c from Clientes c where UPPER(c.nome) LIKE :nome ", Clientes.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getResultList();
		if (listaByNome == null || listaByNome.isEmpty()) {
			throw new Exception("Nenhum cliente encontrado com este nome!");
		}
		return listaByNome;
	}
	
	public List<Clientes> getAll() {
		return manager.createQuery("select e from Clientes e", Clientes.class).getResultList();
	}
	
	public List<Clientes> getAllByRestricoes(Integer ativo) {
		return manager.createQuery("select e from Clientes e where e.ativo = :ativo", Clientes.class)
				.setParameter("ativo", ativo)
				.getResultList();
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
		// Verificando se o cliente a ser excluído possui referência na tabela de empréstimo 
		// pois caso exista um empréstimo para este cliente o mesmo não pode ser excluído.
		List<Emprestimos> listaDeEmprestimosDoCliente = emprestimoService.getByIdCliente(id);
		if (listaDeEmprestimosDoCliente != null && !listaDeEmprestimosDoCliente.isEmpty()) {
			throw new Exception("O cliente não pode ser deletado pois existe(m) registro(s) de empréstimo(s) para ele!");
		}
		// Efetua uma busca em cliente pelo ID onde caso não encontrado será disparado uma exceção.
		Clientes entity = getById(id);
		manager.remove(entity);
	}

	/**
	 * Médodo para validar o cliente antes de inserir ou atualizar no banco.
	 * @param entity de cliente
	 * @throws Exception
	 */
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