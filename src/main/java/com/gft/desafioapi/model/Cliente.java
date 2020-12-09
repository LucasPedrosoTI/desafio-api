package com.gft.desafioapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gft.desafioapi.utils.Coalesce;

@Entity
public class Cliente extends AbstractEntity implements Coalesce<Cliente> {

	private static final long serialVersionUID = 1L;

	private String nome;

	private String email;

	private String senha;

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
