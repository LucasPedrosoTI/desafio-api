package com.gft.desafioapi.dto.fornecedor;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gft.desafioapi.dto.AbstractDTOResponse;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.serializer.CustomProdutosSerializer;

import io.swagger.annotations.ApiModelProperty;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FornecedorDTOResponse extends AbstractDTOResponse {


	@ApiModelProperty(example = "Apple", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2)
	private String nome;

	@ApiModelProperty(example = "89000895000178", allowEmptyValue = false, required = true, notes = "Sem pontos e não permite duplicades")
	@NotBlank
	@CNPJ
	private String cnpj;

	@ApiModelProperty(hidden = true)
	@JsonSerialize(using = CustomProdutosSerializer.class)
	private List<Produto> produtos;

	public FornecedorDTOResponse() {
	}

	public FornecedorDTOResponse(Long id, String nome, String cnpj, List<Produto> produtos) {
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
		FornecedorDTOResponse other = (FornecedorDTOResponse) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(nome, other.nome)
				&& Objects.equals(produtos, other.produtos);
	}

}

