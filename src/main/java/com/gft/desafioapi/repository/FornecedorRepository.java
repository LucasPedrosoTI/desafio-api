package com.gft.desafioapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gft.desafioapi.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
	public Page<Fornecedor> findByNomeContaining(String nome, Pageable pageable);

	@Query("select f from Fornecedor f order by f.nome asc")
	public Page<Fornecedor> findAllOrderByNomeAsc(Pageable pageable);

	@Query("select f from Fornecedor f order by f.nome desc")
	public Page<Fornecedor> findAllOrderByNomeDesc(Pageable pageable);

	public Page<Fornecedor> findByNomeContainingAndCnpjContaining(String nome, String cnpj, Pageable pageable);

}
