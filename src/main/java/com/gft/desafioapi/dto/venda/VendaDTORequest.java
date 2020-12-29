package com.gft.desafioapi.dto.venda;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gft.desafioapi.dto.IdDto;
import com.gft.desafioapi.repository.serializer.CustomProdutosDeserializer;

import io.swagger.annotations.ApiModelProperty;

public class VendaDTORequest {

	@ApiModelProperty(required = true, allowEmptyValue = false, example = "03/12/2020", value = "Data da compra",
			position = -1)
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCompra;

	@NotNull
	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID do cliente")
	private IdDto cliente;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID do fornecedor")
	@NotNull
	private IdDto fornecedor;

	@NotNull
	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID dos produtos")
	@JsonDeserialize(using = CustomProdutosDeserializer.class)
	private List<IdDto> produtos;

	private VendaDTORequest(Builder builder) {
		this.dataCompra = builder.dataCompra;
		this.cliente = builder.cliente;
		this.fornecedor = builder.fornecedor;
		this.produtos = builder.produtos;
	}

	private VendaDTORequest() {}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public IdDto getCliente() {
		return cliente;
	}

	public void setCliente(IdDto cliente) {
		this.cliente = cliente;
	}

	public IdDto getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(IdDto fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<IdDto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<IdDto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cliente, dataCompra, fornecedor, produtos);
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
		VendaDTORequest other = (VendaDTORequest) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(dataCompra, other.dataCompra)
				&& Objects.equals(fornecedor, other.fornecedor) && Objects.equals(produtos, other.produtos);
	}

	/**
	 * Creates builder to build {@link VendaDTORequest}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link VendaDTORequest}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private LocalDate dataCompra;
		private IdDto cliente;
		private IdDto fornecedor;
		private List<IdDto> produtos = Collections.emptyList();

		private Builder() {}

		public Builder withDataCompra(LocalDate dataCompra) {
			this.dataCompra = dataCompra;
			return this;
		}

		public Builder withCliente(IdDto cliente) {
			this.cliente = cliente;
			return this;
		}

		public Builder withFornecedor(IdDto fornecedor) {
			this.fornecedor = fornecedor;
			return this;
		}

		public Builder withProdutos(List<IdDto> produtos) {
			this.produtos = produtos;
			return this;
		}

		public VendaDTORequest build() {
			return new VendaDTORequest(this);
		}
	}

}
