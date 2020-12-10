package com.gft.desafioapi.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorDeserializer;
import com.gft.desafioapi.repository.serializer.CustomProdutoFornecedorSerializer;
import com.gft.desafioapi.utils.Coalesce;

@Entity
public class Produto extends AbstractEntity implements Coalesce<Produto> {

	private static final long serialVersionUID = 1L;

	private String nome;

	private String codigoProduto;

	private BigDecimal valor;

	private Boolean promocao;

	private BigDecimal valorPromo;

	private String imagem;

	@Enumerated(EnumType.STRING)
	private CategoriaEnum categoria;

	private Long quantidade;

	@JsonSerialize(using = CustomProdutoFornecedorSerializer.class)
	@JsonDeserialize(using = CustomProdutoFornecedorDeserializer.class)
	@ManyToOne
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

	public Produto() {
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
		return promocao;
	}

	public void setPromocao(Boolean promocao) {
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(categoria, codigoProduto, fornecedor, imagem, nome, promocao, quantidade,
				valor, valorPromo);
		return result;
	}

	@Override
	public boolean equals(
			Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return categoria == other.categoria && Objects.equals(codigoProduto, other.codigoProduto)
				&& Objects.equals(fornecedor, other.fornecedor) && Objects.equals(imagem, other.imagem)
				&& Objects.equals(nome, other.nome) && Objects.equals(promocao, other.promocao)
				&& Objects.equals(quantidade, other.quantidade) && Objects.equals(valor, other.valor)
				&& Objects.equals(valorPromo, other.valorPromo);
	}

	@Override
	public Produto coalesce(Produto other, Long id) {
		BigDecimal valorPromoCoalesced = null;

		String nomeCoalesced = coalesce(this.getNome(), other.getNome());
		String codigoProdutoCoalesced = coalesce(this.getCodigoProduto(), other.getCodigoProduto());
		BigDecimal valorCoalesced = coalesce(this.getValor(), other.getValor());
		Boolean promocaoCoalesced = coalesce(this.isPromocao(), other.isPromocao());

		if (Boolean.TRUE.equals(promocaoCoalesced)) {
			valorPromoCoalesced = coalesce(this.getValorPromo(), other.getValorPromo());
		}

		String imagemCoalesced = coalesce(this.getImagem(), other.getImagem());
		CategoriaEnum categoriaCoalesced = coalesce(this.getCategoria(), other.getCategoria());
		Long quantidadeCoalesced = coalesce(this.getQuantidade(), other.getQuantidade());
		Fornecedor fornecedorCoalesced = coalesce(this.getFornecedor(), other.getFornecedor());

		return Produto.builder()
				.withId(id)
				.withNome(nomeCoalesced)
				.withCodigoProduto(codigoProdutoCoalesced)
				.withValor(valorCoalesced)
				.withPromocao(promocaoCoalesced)
				.withValorPromo(valorPromoCoalesced)
				.withImagem(imagemCoalesced)
				.withCategoria(categoriaCoalesced)
				.withQuantidade(quantidadeCoalesced)
				.withFornecedor(fornecedorCoalesced)
				.build();
	}

	/**
	 * Creates builder to build {@link Produto}.
	 * 
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Produto}.
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

		public Produto build() {
			return new Produto(this);
		}
	}

}
