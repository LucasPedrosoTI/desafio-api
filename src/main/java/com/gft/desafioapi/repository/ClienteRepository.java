package com.gft.desafioapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gft.desafioapi.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	public Page<Cliente> findByNomeContaining(String nome, Pageable pageable);

	@Query("select c from Cliente c order by c.nome asc")
	public Page<Cliente> findAllOrderByNomeAsc(Pageable pageable);

	@Query("select c from Cliente c order by c.nome desc")
	public Page<Cliente> findAllOrderByNomeDesc(Pageable pageable);
}
