package com.gft.desafioapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.desafioapi.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
	public Page<Fornecedor> findByNomeContaining(String nome, Pageable pageable);
}
