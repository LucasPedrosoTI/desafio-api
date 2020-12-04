package com.gft.desafioapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gft.desafioapi.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	@Query("select v from Venda v where (v.dataCompra BETWEEN ?1 and ?2) and (v.totalCompra BETWEEN ?3 and ?4)")
	public Page<Venda> pesquisarVendas(LocalDate dataCompraDe, LocalDate dataCompraAte, BigDecimal totalCompraDe,
			BigDecimal totalCompraAte, Pageable pageable);

}
