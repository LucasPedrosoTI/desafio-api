package com.gft.desafioapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.gft.desafioapi.utils.Coalesce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
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
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Venda}.
	 */
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
