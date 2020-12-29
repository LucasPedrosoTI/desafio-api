package com.gft.desafioapi.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorDeserializer;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorSerializer;
import com.gft.desafioapi.utils.Coalesce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Produto extends AbstractEntity implements Coalesce<Produto> {

	private static final long serialVersionUID = 1L;

	@Size(min = 2, max = 100)
	private String nome;

	@Size(min = 2, max = 100)
	private String codigoProduto;

	private BigDecimal valor;

	private boolean promocao;

	private BigDecimal valorPromo;

	@Size(min = 2)
	private String imagem;

	@Enumerated(EnumType.STRING)
	private CategoriaEnum categoria;

	private Long quantidade;

	@JsonSerialize(using = CustomProdutoFornecedorSerializer.class)
	@JsonDeserialize(using = CustomProdutoFornecedorDeserializer.class)
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	private Produto(Builder builder) {
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

	@Override
	public Produto coalesce(Produto other, Long id) {
		BigDecimal valorPromoCoalesced = null;

		String nomeCoalesced = this.coalesce(this.getNome(), other.getNome());
		String codigoProdutoCoalesced = this.coalesce(this.getCodigoProduto(), other.getCodigoProduto());
		BigDecimal valorCoalesced = this.coalesce(this.getValor(), other.getValor());
		Boolean promocaoCoalesced = this.coalesce(this.isPromocao(), other.isPromocao());

		if (Boolean.TRUE.equals(promocaoCoalesced)) {
			valorPromoCoalesced = this.coalesce(this.getValorPromo(), other.getValorPromo());
		}

		String imagemCoalesced = this.coalesce(this.getImagem(), other.getImagem());
		CategoriaEnum categoriaCoalesced = this.coalesce(this.getCategoria(), other.getCategoria());
		Long quantidadeCoalesced = this.coalesce(this.getQuantidade(), other.getQuantidade());
		Fornecedor fornecedorCoalesced = this.coalesce(this.getFornecedor(), other.getFornecedor());

		return Produto.builder().withId(id).withNome(nomeCoalesced).withCodigoProduto(codigoProdutoCoalesced)
				.withValor(valorCoalesced).withPromocao(promocaoCoalesced).withValorPromo(valorPromoCoalesced)
				.withImagem(imagemCoalesced).withCategoria(categoriaCoalesced).withQuantidade(quantidadeCoalesced)
				.withFornecedor(fornecedorCoalesced).build();
	}

	public static Builder builder() {
		return new Builder();
	}

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

		private Builder() {}

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

		public Produto build() {
			return new Produto(this);
		}
	}

}
