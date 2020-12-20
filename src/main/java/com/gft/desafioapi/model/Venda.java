package com.gft.desafioapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
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

	@Generated("SparkTools")
	private Venda(Builder builder) {
		super(builder.id);
		this.totalCompra = builder.totalCompra;
		this.dataCompra = builder.dataCompra;
		this.cliente = builder.cliente;
		this.fornecedor = builder.fornecedor;
		this.produtos = builder.produtos;
	}

	private Venda() {}

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

		return builder()
				.withId(id)
				.withDataCompra(dataCompra)
				.withTotalCompra(totalCompra)
				.withCliente(cliente)
				.withFornecedor(fornecedor)
				.withProdutos(produtos)
				.build();
	}

	/**
	 * Creates builder to build {@link Venda}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Venda}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		public Long id;
		private BigDecimal totalCompra;
		private LocalDate dataCompra;
		private Cliente cliente;
		private Fornecedor fornecedor;
		private List<Produto> produtos = Collections.emptyList();

		private Builder() {}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withTotalCompra(BigDecimal totalCompra) {
			this.totalCompra = totalCompra;
			return this;
		}

		public Builder withDataCompra(LocalDate dataCompra) {
			this.dataCompra = dataCompra;
			return this;
		}

		public Builder withCliente(Cliente cliente) {
			this.cliente = cliente;
			return this;
		}

		public Builder withFornecedor(Fornecedor fornecedor) {
			this.fornecedor = fornecedor;
			return this;
		}

		public Builder withProdutos(List<Produto> produtos) {
			this.produtos = produtos;
			return this;
		}

		public Venda build() {
			return new Venda(this);
		}
	}

}
