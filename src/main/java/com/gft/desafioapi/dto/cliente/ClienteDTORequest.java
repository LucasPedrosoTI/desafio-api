package com.gft.desafioapi.dto.cliente;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class ClienteDTORequest {

	@ApiModelProperty(example = "Marco Santos", allowEmptyValue = false, required = true)
	@NotBlank
	@Size(min = 2, max = 100)
	private String nome;

	@ApiModelProperty(example = "email@gft.com", allowEmptyValue = false, required = true, notes = "Não permite duplicidades")
	@NotBlank
	@Email
	private String email;

	@ApiModelProperty(example = "S3nh@123", allowEmptyValue = false, required = true)
	@NotBlank
	private String senha;

	@ApiModelProperty(example = "123456789", allowEmptyValue = false, required = true, notes = "Não permite duplicidades")
	@NotBlank
	private String documento;

	public ClienteDTORequest() {
	}

	public ClienteDTORequest(
			String nome,
			String email,
			String senha,
			String documento) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.documento = documento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(documento, email, nome, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteDTORequest other = (ClienteDTORequest) obj;
		return Objects.equals(documento, other.documento) && Objects.equals(email, other.email)
				&& Objects.equals(nome, other.nome) && Objects.equals(senha, other.senha);
	}


}

