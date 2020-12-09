package com.gft.desafioapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.ClienteDTO;
import com.gft.desafioapi.model.Cliente;

@Component
public class ClienteConverter {

	public ClienteDTO entityToDto(Cliente cliente) {

		return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getSenha(),
				cliente.getDocumento());
	}

	public List<ClienteDTO> entityToDto(List<Cliente> clientes) {
		return clientes.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	public Page<ClienteDTO> entityToDto(Page<Cliente> clientes) {
		return clientes.map(this::entityToDto);
	}

	public Cliente dtoToEntity(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setSenha(dto.getSenha());
		cliente.setDocumento(dto.getDocumento());

		return cliente;
	}

	public List<Cliente> dtoToEntity(List<ClienteDTO> dtos) {
		return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

}
