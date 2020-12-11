package com.gft.desafioapi.dto.fornecedor;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import io.swagger.annotations.ApiModelProperty;

public class FornecedorDTORequest {


	@ApiModelProperty(example = "Apple", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2)
	private String nome;

	@ApiModelProperty(example = "89000895000178", allowEmptyValue = false, required = true, notes = "Sem pontos e não permite duplicades")
	@NotBlank
	@CNPJ
	private String cnpj;


	public FornecedorDTORequest() {
	}

	public FornecedorDTORequest(
			String nome,
			String cnpj) {
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

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FornecedorDTORequest other = (FornecedorDTORequest) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(nome, other.nome);
	}


}

