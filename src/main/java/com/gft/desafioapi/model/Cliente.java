package com.gft.desafioapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Cliente extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(min = 2, max = 100)
	private String nome;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String senha;

	@NotBlank
	private String documento;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCadastro;

	public Cliente() {
	}

	public Cliente(Long id, @NotBlank @Size(min = 2, max = 100) String nome, @NotBlank @Email String email,
			@NotBlank String senha, @NotBlank String documento, LocalDate dataCadastro) {
		super(id);
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.documento = documento;
		this.dataCadastro = dataCadastro;
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

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
