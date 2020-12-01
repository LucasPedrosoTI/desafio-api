package com.gft.desafioapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Fornecedor extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "Xiaomi", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2)
	private String nome;

	@ApiModelProperty(example = "29.762.000/0001-01", allowEmptyValue = false, required = true)
	@NotBlank
	@CNPJ
	@Size(min = 13, max = 15)
	private String cnpj;

	@OneToMany(mappedBy = "fornecedor")
	@JsonManagedReference
	private List<Produto> produtos;

	public Fornecedor() {
	}

	public Fornecedor(Long id, @NotBlank @Size(min = 2) String nome,
			@NotBlank @CNPJ @Size(min = 13, max = 15) String cnpj, List<Produto> produtos) {
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

}
