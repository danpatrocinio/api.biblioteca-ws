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
@Table(name="livro")
@SequenceGenerator(name = "LIVRO_SEQ", sequenceName = "LIVRO_SEQ", allocationSize = 1)
public class Livros {

	@Id
	@Column(name="id_livro")
	@GeneratedValue(generator = "LIVRO_SEQ", strategy = GenerationType.SEQUENCE)
	private Integer idLivro;
	@Column(length=50, nullable=false)
	private String titulo;
	@Column(length=100)
	private String descricao;
	@Column(length=15)
	private String mesAnoLancamento;
	private String isbn;
    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editoras editora;
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autores autor;
	
	public Integer getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMesAnoLancamento() {
		return mesAnoLancamento;
	}
	public void setMesAnoLancamento(String mesAnoLancamento) {
		this.mesAnoLancamento = mesAnoLancamento;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Editoras getEditora() {
		return editora;
	}
	public void setEditora(Editoras editora) {
		this.editora = editora;
	}
	public Autores getAutor() {
		return autor;
	}
	public void setAutor(Autores autor) {
		this.autor = autor;
	}
}