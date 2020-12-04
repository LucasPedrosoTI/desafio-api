package com.gft.desafioapi.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class VendaFilter {

	@ApiModelProperty(value = "Data inicial de compra", allowEmptyValue = true)
	private LocalDate dataCompraDe;

	@ApiModelProperty(value = "Data final de compra", allowEmptyValue = true)
	private LocalDate dataCompraAte;

	@ApiModelProperty(value = "Valor inicial de compra", allowEmptyValue = true)
	private BigDecimal totalCompraDe;

	@ApiModelProperty(value = "Valor total de compra", allowEmptyValue = true)
	private BigDecimal totalCompraAte;

	public VendaFilter() {
	}

	public VendaFilter(LocalDate dataCompraDe, LocalDate dataCompraAte, BigDecimal totalCompraDe,
			BigDecimal totalCompraAte) {
		this.dataCompraDe = dataCompraDe;
		this.dataCompraAte = dataCompraAte;
		this.totalCompraDe = totalCompraDe;
		this.totalCompraAte = totalCompraAte;
	}

	public LocalDate getDataCompraDe() {
		return dataCompraDe;
	}

	public void setDataCompraDe(LocalDate dataCompraDe) {
		this.dataCompraDe = dataCompraDe;
	}

	public LocalDate getDataCompraAte() {
		return dataCompraAte;
	}

	public void setDataCompraAte(LocalDate dataCompraAte) {
		this.dataCompraAte = dataCompraAte;
	}


	public BigDecimal getTotalCompraDe() {
		return totalCompraDe;
	}

	public void setTotalCompraDe(BigDecimal totalCompraDe) {
		this.totalCompraDe = totalCompraDe;
	}

	public BigDecimal getTotalCompraAte() {
		return totalCompraAte;
	}

	public void setTotalCompraAte(BigDecimal totalCompraAte) {
		this.totalCompraAte = totalCompraAte;
	}

}
