package com.gft.desafioapi.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.utils.Coalesce;
import com.gft.desafioapi.utils.CustomProdutoDeserializer;
import com.gft.desafioapi.utils.CustomProdutoSerializer;

import io.swagger.annotations.ApiModelProperty;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Produto extends AbstractEntity implements Coalesce<Produto> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "Redmi Note 9", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2)
	private String nome;

	@ApiModelProperty(example = "XIA01", allowEmptyValue = false, required = true, notes = "Não permite duplicidades")
	@NotBlank
	private String codigoProduto;

	@ApiModelProperty(example = "1199.99", allowEmptyValue = false, required = true)
	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "9999999")
	private BigDecimal valor;

	@ApiModelProperty(example = "true", allowEmptyValue = false, required = true)
	@NotNull
	private Boolean promocao;

	@ApiModelProperty(example = "1000", allowEmptyValue = true, required = false, value = "Caso promocao seja false, valor deve ser nulo")
	@DecimalMin(value = "0")
	@DecimalMax(value = "9999999")
	private BigDecimal valorPromo;

	@ApiModelProperty(example = "redmi-note9.png", allowEmptyValue = false, required = true)
	private String imagem;

	@ApiModelProperty(example = "SMARTPHONE", allowEmptyValue = false, required = true, notes = "Opções: SMARTPHONE,TABLET,NOTEBOOK,MODEM,ROUTER,DESKTOP,ACESSÓRIOS, OUTROS")
	@Enumerated(EnumType.STRING)
	private CategoriaEnum categoria;

	@ApiModelProperty(example = "10", allowEmptyValue = false, required = true)
	@NotNull
	@Min(value = 0)
	@Max(value = 999999)
	private Long quantidade;

//	@JsonBackReference
	@ApiModelProperty(position = 9, required = true, value = "ID do fornecedor")
	@JsonSerialize(using = CustomProdutoSerializer.class)
	@JsonDeserialize(using = CustomProdutoDeserializer.class)
	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	public Produto() {
	}

	public Produto(Long id, @NotBlank @Size(min = 2) String nome, @NotBlank String codigoProduto,
			@NotNull @DecimalMin("0") @DecimalMax("9999999") BigDecimal valor, @NotNull Boolean promocao,
			@NotNull @DecimalMin("0") @DecimalMax("9999999") BigDecimal valorPromo, String imagem,
			CategoriaEnum categoria, @NotNull @Size(min = 0) Long quantidade, Fornecedor fornecedor) {
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

	public Boolean isPromocao() {
		if (promocao instanceof Boolean) {
			return promocao;
		} else {
			return null;
		}
	}

	public void setPromocao(Boolean promocao) {
		if (promocao instanceof Boolean) {
			this.promocao = promocao;
		} else {
			return;
		}
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

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", codigoProduto=" + codigoProduto + ", valor=" + valor + ", promocao="
				+ promocao + ", valorPromo=" + valorPromo + ", imagem=" + imagem + ", categoria=" + categoria
				+ ", quantidade=" + quantidade + ", fornecedor=" + fornecedor + "]";
	}

	@Override
	public Produto coalesce(Produto other, Long id) {
		BigDecimal valorPromo = null;

		String nome = coalesce(this.getNome(), other.getNome());
		String codigoProduto = coalesce(this.getCodigoProduto(), other.getCodigoProduto());
		BigDecimal valor = coalesce(this.getValor(), other.getValor());
		Boolean promocao = coalesce(this.isPromocao(), other.isPromocao());

		if (promocao) {
			valorPromo = coalesce(this.getValorPromo(), other.getValorPromo());
		}

		String imagem = coalesce(this.getImagem(), other.getImagem());
		CategoriaEnum categoria = coalesce(this.getCategoria(), other.getCategoria());
		Long quantidade = coalesce(this.getQuantidade(), other.getQuantidade());
		Fornecedor fornecedor = coalesce(this.getFornecedor(), other.getFornecedor());

		return new Produto(id, nome, codigoProduto, valor, promocao, valorPromo, imagem, categoria, quantidade,
				fornecedor);
	}

}
