package com.gft.desafioapi.repository;

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
}
