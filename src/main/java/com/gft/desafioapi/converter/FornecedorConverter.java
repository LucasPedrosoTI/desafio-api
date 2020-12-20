package com.gft.desafioapi.converter;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.fornecedor.FornecedorDTORequest;
import com.gft.desafioapi.dto.fornecedor.FornecedorDTOResponse;
import com.gft.desafioapi.model.Fornecedor;

@Component
public class FornecedorConverter {

	public FornecedorDTOResponse entityToDtoResponse(Fornecedor fornecedor) {

		return new FornecedorDTOResponse(fornecedor.getId(), fornecedor.getNome(), fornecedor.getCnpj(), fornecedor.getProdutos());
	}

	public Page<FornecedorDTOResponse> entityToDtoResponse(Page<Fornecedor> fornecedores) {
		return fornecedores.map(this::entityToDtoResponse);
	}


	public Fornecedor dtoRequestToEntity(FornecedorDTORequest dto) {

		return Fornecedor.builder()
				.withNome(dto.getNome())
				.withCnpj(dto.getCnpj())
				.build();
	}

}
