package com.gft.desafioapi.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(min = 2)
	private String nome;

	@NotBlank
	private String codigoProduto;

	@NotNull
	@Size(min = 0, max = 9999999)
	private BigDecimal valor;

	@NotNull
	private boolean promocao;

	@NotNull
	@Size(min = 0, max = 9999999)
	private BigDecimal valorPromo;

	@SuppressWarnings("unused")
	private String imagem;

	@Enumerated(EnumType.STRING)
	private CategoriaEnum categoria;

	@NotNull
	@Size(min = 0)
	private String quantidade;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	@JsonBackReference
	private Fornecedor fornecedor;

	public Produto() {
	}

	public Produto(Long id, @NotBlank @Size(min = 2) String nome, @NotBlank String codigoProduto,
			@NotNull @Size(min = 0, max = 9999999) BigDecimal valor, @NotNull boolean promocao,
			@NotNull @Size(min = 0, max = 9999999) BigDecimal valorPromo, String imagem, CategoriaEnum categoria,
			@NotNull @Size(min = 0) String quantidade, Fornecedor fornecedor) {
		super(id);
		this.nome = nome;
		this.codigoProduto = codigoProduto;
		this.valor = valor;
		this.promocao = promocao;
		this.valorPromo = valorPromo;
		this.imagem = imagem;
		this.categoria = categoria;
		this.quantidade = quantidade;
		this.fornecedor = fornecedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isPromocao() {
		return promocao;
	}

	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
	}

	public BigDecimal getValorPromo() {
		return valorPromo;
	}

	public void setValorPromo(BigDecimal valorPromo) {
		this.valorPromo = valorPromo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public CategoriaEnum getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEnum categoria) {
		this.categoria = categoria;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

}
