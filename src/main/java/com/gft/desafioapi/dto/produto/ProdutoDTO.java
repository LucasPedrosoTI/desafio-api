package com.gft.desafioapi.dto.produto;

import java.math.BigDecimal;
import java.util.Objects;

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
import com.gft.desafioapi.dto.AbstractDTO;
import com.gft.desafioapi.model.CategoriaEnum;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorDeserializer;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorSerializer;

import io.swagger.annotations.ApiModelProperty;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProdutoDTO extends AbstractDTO {

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
	private CategoriaEnum categoria;

	@ApiModelProperty(example = "10", allowEmptyValue = false, required = true)
	@NotNull
	@Min(value = 0)
	@Max(value = 9999999)
	private Long quantidade;

	@ApiModelProperty(position = 9, required = true, value = "ID do fornecedor")
	@JsonSerialize(using = CustomProdutoFornecedorSerializer.class)
	@JsonDeserialize(using = CustomProdutoFornecedorDeserializer.class)
	private Fornecedor fornecedor;

	private ProdutoDTO(Builder builder) {
		super(builder.id);
		this.nome = builder.nome;
		this.codigoProduto = builder.codigoProduto;
		this.valor = builder.valor;
		this.promocao = builder.promocao;
		this.valorPromo = builder.valorPromo;
		this.imagem = builder.imagem;
		this.categoria = builder.categoria;
		this.quantidade = builder.quantidade;
		this.fornecedor = builder.fornecedor;
	}

	public ProdutoDTO() {
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(categoria, codigoProduto, fornecedor, imagem, nome, promocao, quantidade,
				valor, valorPromo);
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
		ProdutoDTO other = (ProdutoDTO) obj;
		return categoria == other.categoria && Objects.equals(codigoProduto, other.codigoProduto)
				&& Objects.equals(fornecedor, other.fornecedor) && Objects.equals(imagem, other.imagem)
				&& Objects.equals(nome, other.nome) && Objects.equals(promocao, other.promocao)
				&& Objects.equals(quantidade, other.quantidade) && Objects.equals(valor, other.valor)
				&& Objects.equals(valorPromo, other.valorPromo);
	}

	/**
	 * Creates builder to build {@link ProdutoDTO}.
	 * 
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ProdutoDTO}.
	 */
	public static final class Builder {
		private Long id;
		private String nome;
		private String codigoProduto;
		private BigDecimal valor;
		private Boolean promocao;
		private BigDecimal valorPromo;
		private String imagem;
		private CategoriaEnum categoria;
		private Long quantidade;
		private Fornecedor fornecedor;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withNome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder withCodigoProduto(String codigoProduto) {
			this.codigoProduto = codigoProduto;
			return this;
		}

		public Builder withValor(BigDecimal valor) {
			this.valor = valor;
			return this;
		}

		public Builder withPromocao(Boolean promocao) {
			this.promocao = promocao;
			return this;
		}

		public Builder withValorPromo(BigDecimal valorPromo) {
			this.valorPromo = valorPromo;
			return this;
		}

		public Builder withImagem(String imagem) {
			this.imagem = imagem;
			return this;
		}

		public Builder withCategoria(CategoriaEnum categoria) {
			this.categoria = categoria;
			return this;
		}

		public Builder withQuantidade(Long quantidade) {
			this.quantidade = quantidade;
			return this;
		}

		public Builder withFornecedor(Fornecedor fornecedor) {
			this.fornecedor = fornecedor;
			return this;
		}

		public ProdutoDTO build() {
			return new ProdutoDTO(this);
		}
	}

}
