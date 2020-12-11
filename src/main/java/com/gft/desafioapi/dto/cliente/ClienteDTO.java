package com.gft.desafioapi.dto.cliente;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.gft.desafioapi.dto.AbstractDTO;

import io.swagger.annotations.ApiModelProperty;

public class ClienteDTO extends AbstractDTO {

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

	public ClienteDTO() {
	}

	public ClienteDTO(Long id, String nome, String email, String senha, String documento) {
		super(id);
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(documento, email, this.getId(), nome, senha);
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
		ClienteDTO other = (ClienteDTO) obj;
		return Objects.equals(documento, other.documento) && Objects.equals(email, other.email)
				&& Objects.equals(super.getId(), other.getId()) && Objects.equals(nome, other.nome)
				&& Objects.equals(senha, other.senha);
	}

}

