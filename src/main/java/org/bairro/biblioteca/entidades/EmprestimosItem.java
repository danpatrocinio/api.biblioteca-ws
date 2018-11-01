package org.bairro.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="emprestimo_item")
@SequenceGenerator(name = "EMPRESTIMO_ITEM_SEQ", sequenceName = "EMPRESTIMO_ITEM_SEQ", allocationSize = 1)
public class EmprestimosItem {

	@Id
	@Column(name="id_emprestimo_item")
	@GeneratedValue(generator = "EMPRESTIMO_ITEM_SEQ", strategy = GenerationType.SEQUENCE)
	private Integer idEmprestimoItem;

	@ManyToOne
    @JoinColumn(name = "id_livro")
    private Livros livro;
	
    @Column(nullable = false)
    private Integer quantidade;

	public Integer getIdEmprestimoItem() {
		return idEmprestimoItem;
	}

	public void setIdEmprestimoItem(Integer idEmprestimoItem) {
		this.idEmprestimoItem = idEmprestimoItem;
	}

	public Livros getLivro() {
		return livro;
	}

	public void setLivro(Livros livro) {
		this.livro = livro;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
    
}