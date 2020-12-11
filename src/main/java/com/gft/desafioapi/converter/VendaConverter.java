package com.gft.desafioapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.venda.VendaDTO;
import com.gft.desafioapi.dto.venda.VendaDTORequest;
import com.gft.desafioapi.model.Venda;

@Component
public class VendaConverter {

	public VendaDTO entityToDto(Venda venda) {

		return new VendaDTO(venda.getId(), venda.getTotalCompra(), venda.getDataCompra(), venda.getCliente(),
				venda.getFornecedor(), venda.getProdutos());
	}

	public List<VendaDTO> entityToDto(List<Venda> vendas) {
		return vendas.stream()
				.map(this::entityToDto)
				.collect(Collectors.toList());
	}

	public Page<VendaDTO> entityToDto(Page<Venda> vendas) {
		return vendas.map(this::entityToDto);
	}

	public Venda dtoToEntity(VendaDTO dto) {
		return new Venda(dto.getId(), dto.getTotalCompra(), dto.getDataCompra(), dto.getCliente(),
				dto.getFornecedor(), dto.getProdutos());
	}

	public Venda dtoToEntity(VendaDTORequest dto) {

		Venda venda = new Venda();

		venda.setDataCompra(dto.getDataCompra());
		venda.setTotalCompra(dto.getTotalCompra());
		venda.setCliente(dto.getCliente());
		venda.setFornecedor(dto.getFornecedor());
		venda.setProdutos(dto.getProdutos());

		return venda;
	}

	public List<Venda> dtoToEntity(List<VendaDTO> dtos) {
		return dtos.stream()
				.map(this::dtoToEntity)
				.collect(Collectors.toList());
	}
}
