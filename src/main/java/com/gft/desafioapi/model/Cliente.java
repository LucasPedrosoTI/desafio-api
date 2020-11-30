package com.gft.desafioapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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
}
