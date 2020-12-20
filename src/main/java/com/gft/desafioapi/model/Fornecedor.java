package com.gft.desafioapi.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.gft.desafioapi.utils.Coalesce;

@Entity
public class Fornecedor extends AbstractEntity implements Coalesce<Fornecedor> {

	private static final long serialVersionUID = 1L;

	@Size(min = 2, max = 100)
	private String nome;

	@CNPJ
	private String cnpj;

	@OneToMany(mappedBy = "fornecedor")
	private List<Produto> produtos;

	@Generated("SparkTools")
	private Fornecedor(Builder builder) {
		super(builder.id);
		this.nome = builder.nome;
		this.cnpj = builder.cnpj;
		this.produtos = builder.produtos;
	}

	//	public Fornecedor() {
	//	}
	//
	//	public Fornecedor(Long id, String nome, String cnpj, List<Produto> produtos) {
	//		super(id);
	//		this.nome = nome;
	//		this.cnpj = cnpj;
	//		this.produtos = produtos;
	//	}

	public Fornecedor(
			Long id) {
		super(id);
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return "Fornecedor [nome=" + this.nome + ", cnpj=" + this.cnpj + ", Id=" + this.getId() + "]";
	}

	@Override
	public Fornecedor coalesce(Fornecedor other, Long id) {

		String nome = this.coalesce(this.getNome(), other.getNome());
		String cnpj = this.coalesce(this.getCnpj(), other.getCnpj());
		List<Produto> produtos = this.coalesce(this.getProdutos(), other.getProdutos());

		return builder()
				.withId(id)
				.withNome(nome)
				.withCnpj(cnpj)
				.withProdutos(produtos)
				.build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.cnpj, this.nome, this.produtos);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(this.cnpj, other.cnpj) && Objects.equals(this.nome, other.nome)
				&& Objects.equals(this.produtos, other.produtos);
	}

	/**
	 * Creates builder to build {@link Fornecedor}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Fornecedor}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		public Long id;
		private String nome;
		private String cnpj;
		private List<Produto> produtos = Collections.emptyList();

		private Builder() {}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withNome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder withCnpj(String cnpj) {
			this.cnpj = cnpj;
			return this;
		}

		public Builder withProdutos(List<Produto> produtos) {
			this.produtos = produtos;
			return this;
		}

		public Fornecedor build() {
			return new Fornecedor(this);
		}
	}

}
