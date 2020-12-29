package com.gft.desafioapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gft.desafioapi.utils.Coalesce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
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

	private Cliente(Builder builder) {
		super(builder.id);
		this.nome = builder.nome;
		this.email = builder.email;
		this.senha = builder.senha;
		this.documento = builder.documento;
		this.dataCadastro = builder.dataCadastro;
	}

	@Override
	public Cliente coalesce(Cliente other, Long id) {

		String nome = this.coalesce(this.getNome(), other.getNome());
		String email = this.coalesce(this.getEmail(), other.getEmail());
		String senha = this.coalesce(this.getSenha(), other.getSenha());
		String documento = this.coalesce(this.getDocumento(), other.getDocumento());
		LocalDate dataCadastro = this.coalesce(this.getDataCadastro(), other.getDataCadastro());

		return builder()
				.withId(id)
				.withNome(nome)
				.withEmail(email)
				.withSenha(senha)
				.withDocumento(documento)
				.withDataCadastro(dataCadastro)
				.build();

	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Long id;
		private String nome;
		private String email;
		private String senha;
		private String documento;
		private LocalDate dataCadastro;

		private Builder() {}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withNome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder withSenha(String senha) {
			this.senha = senha;
			return this;
		}

		public Builder withDocumento(String documento) {
			this.documento = documento;
			return this;
		}

		public Builder withDataCadastro(LocalDate dataCadastro) {
			this.dataCadastro = dataCadastro;
			return this;
		}

		public Cliente build() {
			return new Cliente(this);
		}
	}

}
