package com.gft.desafioapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class ClienteDTO {

	@ApiModelProperty(example = "1", allowEmptyValue = true, required = false, position = -1)
	private Long id;

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
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.documento = documento;
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

}

