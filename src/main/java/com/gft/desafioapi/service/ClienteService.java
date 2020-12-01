package com.gft.desafioapi.service;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	public Cliente create(Cliente cliente) {

		if (Objects.isNull(cliente.getDataCadastro())) {
			cliente.setDataCadastro(LocalDate.now());
		}

		return clienteRepository.save(cliente);

	}
}
