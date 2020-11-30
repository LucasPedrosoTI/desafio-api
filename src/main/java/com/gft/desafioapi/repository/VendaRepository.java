package com.gft.desafioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.desafioapi.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
