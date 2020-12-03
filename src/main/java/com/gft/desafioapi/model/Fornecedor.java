package com.gft.desafioapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.gft.desafioapi.utils.Coalesce;

import io.swagger.annotations.ApiModelProperty;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Fornecedor extends AbstractEntity implements Coalesce<Fornecedor> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "Apple", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2)
	private String nome;

	@ApiModelProperty(example = "89000895000178", allowEmptyValue = false, required = true, notes = "Sem pontos e não permite duplicades")
	@NotBlank
	@CNPJ()
	private String cnpj;

	@ApiModelProperty(hidden = true)
	@OneToMany(mappedBy = "fornecedor")
//	@JsonManagedReference
	private List<Produto> produtos;

	public Fornecedor() {
	}

	public Fornecedor(Long id, @NotBlank @Size(min = 2) String nome, @NotBlank @CNPJ String cnpj,
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
