package com.gft.desafioapi.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gft.desafioapi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public Page<Produto> findByNomeContaining(String nome, Pageable pageable);

	@Query("select p from Produto p order by p.nome asc")
	public Page<Produto> findAllOrderByNomeAsc(Pageable pageable);

	@Query("select p from Produto p order by p.nome desc")
	public Page<Produto> findAllOrderByNomeDesc(Pageable pageable);

	//@formatter:off
	@Query(
			"select p from Produto p where "
					+ "p.nome like %?1% and p.codigoProduto like %?2% and "
					+ "(p.valor BETWEEN ?3 and ?4) and "
					+ "(p.quantidade BETWEEN ?5 and ?6) and "
					+ "(p.valorPromo IS NULL OR (p.valorPromo BETWEEN ?7 AND ?8))"
			)
	//@formatter:on
	public Page<Produto> pesquisarProdutos(String nome, String codigoProduto, BigDecimal valorDe, BigDecimal valorAte,
			Long quantidadeDe, Long quantidadeAte, BigDecimal valorPromoDe, BigDecimal valorPromoAte,
			Pageable pageable);
}
