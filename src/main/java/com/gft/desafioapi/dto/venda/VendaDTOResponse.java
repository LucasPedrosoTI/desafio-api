package com.gft.desafioapi.dto.venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.dto.AbstractDTOResponse;
import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorSerializer;
import com.gft.desafioapi.repository.serializer.CustomProdutosDeserializer;
import com.gft.desafioapi.repository.serializer.CustomProdutosSerializer;
import com.gft.desafioapi.repository.serializer.CustomVendaClienteSerializer;

import io.swagger.annotations.ApiModelProperty;

public class VendaDTOResponse extends AbstractDTOResponse {

	@ApiModelProperty(hidden = true)
	@DecimalMin(value = "0")
	@DecimalMax(value = "9999999")
	private BigDecimal totalCompra;

	@ApiModelProperty(required = true, allowEmptyValue = false, example = "03/12/2020", value = "Data da compra")
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCompra;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID do cliente")
	@JsonSerialize(using = CustomVendaClienteSerializer.class)
	private Cliente cliente;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID do fornecedor")
	@JsonSerialize(using = CustomProdutoFornecedorSerializer.class)
	@NotNull
	private Fornecedor fornecedor;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID dos produtos")
	@JsonSerialize(using = CustomProdutosSerializer.class)
	@JsonDeserialize(using = CustomProdutosDeserializer.class)
	private List<Produto> produtos;

	private VendaDTOResponse(Builder builder) {
		super(builder.id);
		this.totalCompra = builder.totalCompra;
		this.dataCompra = builder.dataCompra;
		this.cliente = builder.cliente;
		this.fornecedor = builder.fornecedor;
		this.produtos = builder.produtos;
	}

	private VendaDTOResponse() {}

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

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
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
		VendaDTOResponse other = (VendaDTOResponse) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(dataCompra, other.dataCompra)
				&& Objects.equals(fornecedor, other.fornecedor) && Objects.equals(produtos, other.produtos)
				&& Objects.equals(totalCompra, other.totalCompra);
	}

	/**
	 * Creates builder to build {@link VendaDTOResponse}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link VendaDTOResponse}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
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

		public VendaDTOResponse build() {
			return new VendaDTOResponse(this);
		}
	}

}
