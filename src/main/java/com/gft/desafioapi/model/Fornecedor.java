package com.gft.desafioapi.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.gft.desafioapi.utils.Coalesce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Fornecedor extends AbstractEntity implements Coalesce<Fornecedor> {

	private static final long serialVersionUID = 1L;

	@Size(min = 2, max = 100)
	private String nome;

	@CNPJ
	private String cnpj;

	@OneToMany(mappedBy = "fornecedor")
	private List<Produto> produtos;

	private Fornecedor(Builder builder) {
		super(builder.id);
		this.nome = builder.nome;
		this.cnpj = builder.cnpj;
		this.produtos = builder.produtos;
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

	public static Builder builder() {
		return new Builder();
	}

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
