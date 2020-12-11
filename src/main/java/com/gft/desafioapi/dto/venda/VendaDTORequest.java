package com.gft.desafioapi.dto.venda;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gft.desafioapi.dto.AbstractDtoId;
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
	private AbstractDtoId cliente;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID do fornecedor")
	@NotNull
	private AbstractDtoId fornecedor;

	@NotNull
	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID dos produtos")
	@JsonDeserialize(using = CustomProdutosDeserializer.class)
	private List<AbstractDtoId> produtos;

	public VendaDTORequest() {}

	public VendaDTORequest(

			LocalDate dataCompra,
			AbstractDtoId cliente,
			AbstractDtoId fornecedor,
			List<AbstractDtoId> produtos) {

		this.dataCompra = dataCompra;
		this.cliente = cliente;
		this.fornecedor = fornecedor;
		this.produtos = produtos;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public AbstractDtoId getCliente() {
		return cliente;
	}

	public void setCliente(AbstractDtoId cliente) {
		this.cliente = cliente;
	}

	public AbstractDtoId getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(AbstractDtoId fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<AbstractDtoId> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<AbstractDtoId> produtos) {
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

}
