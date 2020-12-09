package com.gft.desafioapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
import com.gft.desafioapi.repository.serializer.CustomVendaClienteSerializer;
import com.gft.desafioapi.repository.serializer.CustomVendaProdutoDeserializer;
import com.gft.desafioapi.repository.serializer.CustomVendaProdutoSerializer;

import io.swagger.annotations.ApiModelProperty;

public class VendaDTO {

	private Long id;

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
	@JsonSerialize(using = CustomVendaProdutoSerializer.class)
	@JsonDeserialize(using = CustomVendaProdutoDeserializer.class)
	private List<Produto> produtos;

	public VendaDTO() {}

	public VendaDTO(
			Long id,
			BigDecimal totalCompra,
			LocalDate dataCompra,
			Cliente cliente,
			Fornecedor fornecedor,
			List<Produto> produtos) {
		this.id = id;
		this.totalCompra = totalCompra;
		this.dataCompra = dataCompra;
		this.cliente = cliente;
		this.fornecedor = fornecedor;
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


}
