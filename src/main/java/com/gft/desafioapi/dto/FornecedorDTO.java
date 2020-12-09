package com.gft.desafioapi.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.gft.desafioapi.model.Produto;

import io.swagger.annotations.ApiModelProperty;

public class FornecedorDTO {

	@ApiModelProperty(example = "1", allowEmptyValue = true, required = false, position = -1)
	private Long id;

	@ApiModelProperty(example = "Apple", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2)
	private String nome;

	@ApiModelProperty(example = "89000895000178", allowEmptyValue = false, required = true, notes = "Sem pontos e não permite duplicades")
	@NotBlank
	@CNPJ
	private String cnpj;

	@ApiModelProperty(hidden = true)
	private List<Produto> produtos;

	public FornecedorDTO() {
	}

	public FornecedorDTO(Long id, String nome, String cnpj, List<Produto> produtos) {
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

