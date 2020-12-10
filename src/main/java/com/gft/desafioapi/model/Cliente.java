package com.gft.desafioapi.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gft.desafioapi.utils.Coalesce;

@Entity
public class Cliente extends AbstractEntity implements Coalesce<Cliente> {

	private static final long serialVersionUID = 1L;

	@Size(min = 2, max = 100)
	private String nome;

	@Email
	private String email;

	@Size(min = 6, max = 100)
	private String senha;

	@Size(min = 2, max = 100)
	private String documento;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro;

	public Cliente() {
	}

	public Cliente(Long id, String nome, String email, String senha, String documento, LocalDate dataCadastro) {
		super(id);
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.documento = documento;
		this.dataCadastro = dataCadastro;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public LocalDate getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + this.nome + ", email=" + this.email + ", senha=" + this.senha + ", documento="
				+ this.documento + ", dataCadastro=" + this.dataCadastro + ", getId()=" + this.getId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.dataCadastro, this.documento, this.email, this.nome, this.senha);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(this.dataCadastro, other.dataCadastro) && Objects.equals(this.documento, other.documento)
				&& Objects.equals(this.email, other.email) && Objects.equals(this.nome, other.nome)
				&& Objects.equals(this.senha, other.senha);
	}

	@Override
	public Cliente coalesce(Cliente other, Long id) {

		String nome = this.coalesce(this.getNome(), other.getNome());
		String email = this.coalesce(this.getEmail(), other.getEmail());
		String senha = this.coalesce(this.getSenha(), other.getSenha());
		String documento = this.coalesce(this.getDocumento(), other.getDocumento());
		LocalDate dataCadastro = this.coalesce(this.getDataCadastro(), other.getDataCadastro());

		return new Cliente(id, nome, email, senha, documento, dataCadastro);
	}

}
