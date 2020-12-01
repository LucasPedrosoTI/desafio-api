package com.gft.desafioapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Venda extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 0)
	private BigDecimal totalCompra;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCompra;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToMany
	@JoinTable(name = "venda_produto", joinColumns = @JoinColumn(name = "venda_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
	private List<Produto> produtos;

	public Venda() {
	}

	public Venda(Long id, @NotNull @Size(min = 0) BigDecimal totalCompra, @NotNull LocalDate dataCompra,
			Cliente cliente, List<Produto> produtos) {
		super(id);
		this.totalCompra = totalCompra;
		this.dataCompra = dataCompra;
		this.cliente = cliente;
		this.produtos = produtos;
	}

	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
