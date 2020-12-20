package com.gft.desafioapi.repository.filter;

import java.math.BigDecimal;

import com.gft.desafioapi.utils.Constants;

public class ProdutoFilter {

	private String nome = "";
	private String codigoProduto = "";
	private BigDecimal valorDe = BigDecimal.ZERO;
	private BigDecimal valorAte = Constants.MAX_DECIMAL;
	private BigDecimal valorPromoDe = BigDecimal.ZERO;
	private BigDecimal valorPromoAte = Constants.MAX_DECIMAL;
	private Long quantidadeDe = 0L;
	private Long quantidadeAte = Constants.MAX_DECIMAL.longValue();

	public ProdutoFilter(String nome, String codigoProduto, BigDecimal valorDe, BigDecimal valorAte,
			BigDecimal valorPromoDe, BigDecimal valorPromoAte, Long quantidadeDe, Long quantidadeAte) {
		this.nome = nome;
		this.codigoProduto = codigoProduto;
		this.valorDe = valorDe;
		this.valorAte = valorAte;
		this.valorPromoDe = valorPromoDe;
		this.valorPromoAte = valorPromoAte;
		this.quantidadeDe = quantidadeDe;
		this.quantidadeAte = quantidadeAte;
	}

	public ProdutoFilter() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public BigDecimal getValorDe() {
		return valorDe;
	}

	public void setValorDe(BigDecimal valorDe) {
		this.valorDe = valorDe;
	}

	public BigDecimal getValorAte() {
		return valorAte;
	}

	public void setValorAte(BigDecimal valorAte) {
		this.valorAte = valorAte;
	}

	public BigDecimal getValorPromoDe() {
		return valorPromoDe;
	}

	public void setValorPromoDe(BigDecimal valorPromoDe) {
		this.valorPromoDe = valorPromoDe;
	}

	public BigDecimal getValorPromoAte() {
		return valorPromoAte;
	}

	public void setValorPromoAte(BigDecimal valorPromoAte) {
		this.valorPromoAte = valorPromoAte;
	}

	public Long getQuantidadeDe() {
		return quantidadeDe;
	}

	public void setQuantidadeDe(Long quantidadeDe) {
		this.quantidadeDe = quantidadeDe;
	}

	public Long getQuantidadeAte() {
		return quantidadeAte;
	}

	public void setQuantidadeAte(Long quantidadeAte) {
		this.quantidadeAte = quantidadeAte;
	}

}
