package com.gft.desafioapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gft.desafioapi.utils.Coalesce;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Cliente extends AbstractEntity implements Coalesce<Cliente> {

	private static final long serialVersionUID = 1L;

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

	@ApiModelProperty(example = "2020-11-30", allowEmptyValue = true, required = false, hidden = true)
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

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", email=" + email + ", senha=" + senha + ", documento=" + documento
				+ ", dataCadastro=" + dataCadastro + ", getId()=" + getId() + "]";
	}

	@Override
	public Cliente coalesce(Cliente other, Long id) {

		String nome = coalesce(this.getNome(), other.getNome());
		String email = coalesce(this.getEmail(), other.getEmail());
		String senha = coalesce(this.getSenha(), other.getSenha());
		String documento = coalesce(this.getDocumento(), other.getDocumento());
		LocalDate dataCadastro = coalesce(this.getDataCadastro(), other.getDataCadastro());

		return new Cliente(id, nome, email, senha, documento, dataCadastro);
	}

}
