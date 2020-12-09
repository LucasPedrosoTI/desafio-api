package com.gft.desafioapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.br.CNPJ;

import com.gft.desafioapi.utils.Coalesce;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

}
