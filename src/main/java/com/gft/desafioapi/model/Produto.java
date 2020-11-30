package com.gft.desafioapi.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Produto extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(min = 2)
	private String nome;

	@NotBlank
	private String codigoProduto;

	@NotNull
	@Size(min = 0, max = 9999999)
	private BigDecimal valor;

	@NotNull
	private boolean promocao;

	@NotNull
	@Size(min = 0, max = 9999999)
	private BigDecimal valorPromo;

	@SuppressWarnings("unused")
	private String imagem;

	@Enumerated(EnumType.STRING)
	private CategoriaEnum categoria;

	@NotNull
	@Size(min = 0)
	private String quantidade;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;
}
