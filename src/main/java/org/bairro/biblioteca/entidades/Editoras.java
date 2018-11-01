package org.bairro.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="editora")
@SequenceGenerator(name = "EDITORA_SEQ", sequenceName = "EDITORA_SEQ", allocationSize = 1)
public class Editoras {

	@Id
	@Column(name="id_editora")
	@GeneratedValue(generator = "EDITORA_SEQ", strategy = GenerationType.SEQUENCE)
	private Integer idEditora;
	@Column(length=50, nullable=false)
	private String nome;
	public Integer getIdEditora() {
		return idEditora;
	}
	public void setIdEditora(Integer idEditora) {
		this.idEditora = idEditora;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}