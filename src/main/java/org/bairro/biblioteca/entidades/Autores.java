package org.bairro.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="autor")
@SequenceGenerator(name = "AUTOR_SEQ", sequenceName = "AUTOR_SEQ", allocationSize = 1)
public class Autores {

	@Id
	@Column(name="id_autor")
	@GeneratedValue(generator = "AUTOR_SEQ", strategy = GenerationType.SEQUENCE)
	private Integer idAutor;
	@Column(length=50, nullable=false)
	private String nome;
	public Integer getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}