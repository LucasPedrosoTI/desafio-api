package com.gft.desafioapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	VendaRepository vendaRepository;

	public Venda findProdutoById(Long id) {
		return vendaRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

}
