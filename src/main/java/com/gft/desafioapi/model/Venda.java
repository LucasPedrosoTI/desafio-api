package com.gft.desafioapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.gft.desafioapi.utils.Coalesce;

@Entity
public class Venda extends AbstractEntity implements Coalesce<Venda> {

	private static final long serialVersionUID = 1L;

	private BigDecimal totalCompra;

	private LocalDate dataCompra;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	@ManyToMany
	@JoinTable(name = "venda_produto", joinColumns = @JoinColumn(name = "venda_id"),
	inverseJoinColumns = @JoinColumn(name = "produto_id"))
	private List<Produto> produtos;

	public Venda() {}

	public Venda(
			Long id,
			BigDecimal totalCompra,
			LocalDate dataCompra,
			Cliente cliente,
			Fornecedor fornecedor,
			List<Produto> produtos) {
		super(id);
		this.totalCompra = totalCompra;
		this.dataCompra = dataCompra;
		this.cliente = cliente;
		this.fornecedor = fornecedor;
		this.produtos = produtos;
	}

	public BigDecimal getTotalCompra() { return totalCompra; }

	public void setTotalCompra(BigDecimal totalCompra) { this.totalCompra = totalCompra; }

	public LocalDate getDataCompra() { return dataCompra; }

	public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

	public Cliente getCliente() { return cliente; }

	public void setCliente(Cliente cliente) { this.cliente = cliente; }

	public Fornecedor getFornecedor() { return fornecedor; }

	public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }

	public List<Produto> getProdutos() { return produtos; }

	public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }

	@Override
	public String toString() {
		return "{" + " totalCompra='" + getTotalCompra() + "'" + ", dataCompra='" + getDataCompra() + "'"
				+ ", cliente='"
				+ getCliente() + "'" + ", fornecedor='" + getFornecedor() + "'" + ", produtos='" + getProdutos() + "'"
				+ "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cliente, dataCompra, fornecedor, produtos, totalCompra);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(dataCompra, other.dataCompra)
				&& Objects.equals(fornecedor, other.fornecedor) && Objects.equals(produtos, other.produtos)
				&& Objects.equals(totalCompra, other.totalCompra);
	}

	@Override
	public Venda coalesce(Venda other, Long id) {
		BigDecimal totalCompra = coalesce(this.totalCompra, other.totalCompra);
		LocalDate dataCompra = coalesce(this.dataCompra, other.dataCompra);
		Cliente cliente = coalesce(this.cliente, other.cliente);
		Fornecedor fornecedor = coalesce(this.fornecedor, other.fornecedor);
		List<Produto> produtos = coalesce(this.produtos, other.produtos);

		return new Venda(id, totalCompra, dataCompra, cliente, fornecedor, produtos);
	}

}
