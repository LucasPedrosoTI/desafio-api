package com.gft.desafioapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.FornecedorDTO;
import com.gft.desafioapi.model.Fornecedor;

@Component
public class FornecedorConverter {

	public FornecedorDTO entityToDto(Fornecedor fornecedor) {

		return new FornecedorDTO(fornecedor.getId(), fornecedor.getNome(), fornecedor.getCnpj(), fornecedor.getProdutos());
	}

	public List<FornecedorDTO> entityToDto(List<Fornecedor> fornecedores) {
		return fornecedores.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	public Page<FornecedorDTO> entityToDto(Page<Fornecedor> fornecedores) {
		return fornecedores.map(this::entityToDto);
	}

	public Fornecedor dtoToEntity(FornecedorDTO dto) {
		Fornecedor fornecedor = new Fornecedor();

		BeanUtils.copyProperties(dto, fornecedor);

		return fornecedor;
	}

	public List<Fornecedor> dtoToEntity(List<FornecedorDTO> dtos) {
		return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

}
