package com.gft.desafioapi.repository.filter;

import io.swagger.annotations.ApiModelProperty;

public class FornecedorFilter {

	@ApiModelProperty(value = "Nome do fornecedor", allowEmptyValue = true)
	private String nome = "";

	@ApiModelProperty(value = "CNPJ do fornecedor", allowEmptyValue = true)
	private String cnpj = "";

	public FornecedorFilter() {
	}

	public FornecedorFilter(String nome, String cnpj) {
		this.nome = nome;
		this.cnpj = cnpj;
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

}
