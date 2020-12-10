package com.gft.desafioapi.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.br.CNPJ;

import com.gft.desafioapi.utils.Coalesce;


@Entity
public class Fornecedor extends AbstractEntity implements Coalesce<Fornecedor> {

	private static final long serialVersionUID = 1L;

	private String nome;

	@CNPJ
	private String cnpj;

	@OneToMany(mappedBy = "fornecedor")
	private List<Produto> produtos;

	public Fornecedor() {
	}

	public Fornecedor(Long id, String nome, String cnpj,
			List<Produto> produtos) {
		super(id);
		this.nome = nome;
		this.cnpj = cnpj;
		this.produtos = produtos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return "Fornecedor [nome=" + nome + ", cnpj=" + cnpj + ", Id=" + getId() + "]";
	}

	@Override
	public Fornecedor coalesce(Fornecedor other, Long id) {

		String nome = coalesce(this.getNome(), other.getNome());
		String cnpj = coalesce(this.getCnpj(), other.getCnpj());
		List<Produto> produtos = coalesce(this.getProdutos(), other.getProdutos());

		return new Fornecedor(id, nome, cnpj, produtos);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cnpj, nome, produtos);
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
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(nome, other.nome)
				&& Objects.equals(produtos, other.produtos);
	}

}
