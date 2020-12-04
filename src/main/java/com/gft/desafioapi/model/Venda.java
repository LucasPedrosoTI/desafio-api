package com.gft.desafioapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.repository.serializer.CustomVendaClienteSerializer;
import com.gft.desafioapi.repository.serializer.CustomVendaProdutoDeserializer;
import com.gft.desafioapi.repository.serializer.CustomVendaProdutoSerializer;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Venda extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(hidden = true)
	@DecimalMin(value = "0")
	private BigDecimal totalCompra;

	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCompra;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID do cliente")
	@JsonSerialize(using = CustomVendaClienteSerializer.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ApiModelProperty(allowEmptyValue = false, required = true, value = "ID dos produtos")
	@JsonSerialize(using = CustomVendaProdutoSerializer.class)
	@JsonDeserialize(using = CustomVendaProdutoDeserializer.class)
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

	@Override
	public String toString() {
		return "Venda [totalCompra=" + totalCompra + ", dataCompra=" + dataCompra + ", cliente=" + cliente
				+ ", produtos=" + produtos + ", getId()=" + getId() + "]";
	}

}
