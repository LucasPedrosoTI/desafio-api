package com.gft.desafioapi.converter;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.cliente.ClienteDTORequest;
import com.gft.desafioapi.dto.cliente.ClienteDTOResponse;
import com.gft.desafioapi.model.Cliente;

@Component
public class ClienteConverter {

	public ClienteDTOResponse entityToDtoResponse(Cliente cliente) {
		return new ClienteDTOResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getSenha(),
				cliente.getDocumento());
	}

	public Page<ClienteDTOResponse> entityToDtoResponse(Page<Cliente> clientes) {
		return clientes.map(this::entityToDtoResponse);
	}

	public Cliente dtoRequestToEntity(ClienteDTORequest dto) {
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setSenha(dto.getSenha());
		cliente.setDocumento(dto.getDocumento());

		return cliente;
	}

}
