package com.gft.desafioapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorSerializer;
import com.gft.desafioapi.repository.serializer.CustomProdutosDeserializer;
import com.gft.desafioapi.repository.serializer.CustomProdutosSerializer;
import com.gft.desafioapi.repository.serializer.CustomVendaClienteSerializer;

import io.swagger.annotations.ApiModelProperty;

public class VendaDTO extends AbstractDTO {

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

	public VendaDTO() {}

	public VendaDTO(
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
		VendaDTO other = (VendaDTO) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(dataCompra, other.dataCompra)
				&& Objects.equals(fornecedor, other.fornecedor) && Objects.equals(produtos, other.produtos)
				&& Objects.equals(totalCompra, other.totalCompra);
	}

}
