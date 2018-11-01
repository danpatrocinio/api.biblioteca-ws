package org.bairro.biblioteca.entidades;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="emprestimo")
@SequenceGenerator(name = "EMPRESTIMO_SEQ", sequenceName = "EMPRESTIMO_SEQ", allocationSize = 1)
public class Emprestimos {

	@Id
	@Column(name="id_emprestimo")
	@GeneratedValue(generator = "EMPRESTIMO_SEQ", strategy = GenerationType.SEQUENCE)
	private Integer idEmprestimo;

	@Column(name = "DT_EMPRESTIMO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmprestimo;

	@Column(name = "DT_PREV_DEVOLUCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrevisaoDevolucao;
	
	@Column(name = "DT_DEVOLUCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDevolucao;

    @Column(name = "VL_MULTA", precision = 15, scale = 5)
	private BigDecimal valorMulta;
	
	@Column(length = 100)
	private String observacoes;
	
	@ManyToOne
    @JoinColumn(name = "id_cliente")
	private Clientes cliente;
	
	@JoinColumn(name = "id_emprestimo")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmprestimosItem> itens;

	public Integer getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(Integer idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getDataPrevisaoDevolucao() {
		return dataPrevisaoDevolucao;
	}

	public void setDataPrevisaoDevolucao(Date dataPrevisaoDevolucao) {
		this.dataPrevisaoDevolucao = dataPrevisaoDevolucao;
	}

	public BigDecimal getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta) {
		this.valorMulta = valorMulta;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<EmprestimosItem> getItens() {
		return itens;
	}

	public void setItens(List<EmprestimosItem> itens) {
		this.itens = itens;
	}
}