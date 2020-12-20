package com.gft.desafioapi.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.produto.ProdutoDTORequest;
import com.gft.desafioapi.dto.produto.ProdutoDTOResponse;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.FornecedorRepository;

@Component
public class ProdutoConverter {

	@Autowired
	FornecedorRepository fornecedorRepository;

	public ProdutoDTOResponse entityToDtoResponse(Produto produto) {

		return ProdutoDTOResponse.builder()
				.withId(produto.getId())
				.withNome(produto.getNome())
				.withValor(produto.getValor())
				.withPromocao(produto.isPromocao())
				.withValorPromo(produto.getValorPromo())
				.withImagem(produto.getImagem())
				.withCategoria(produto.getCategoria())
				.withCodigoProduto(produto.getCodigoProduto())
				.withQuantidade(produto.getQuantidade())
				.withFornecedor(produto.getFornecedor())
				.build();
	}


	public Page<ProdutoDTOResponse> entityToDtoResponse(Page<Produto> produtos) {
		return produtos.map(this::entityToDtoResponse);
	}


	public Produto dtoRequestToEntity(ProdutoDTORequest dto) {

		Fornecedor fornecedor = null;

		if (dtoHasFornecedor(dto) && fornecedorHasId(dto)) {
			fornecedor = fornecedorRepository.getOne(dto.getFornecedor().getId());
		}

		return Produto.builder()
				.withNome(dto.getNome())
				.withValor(dto.getValor())
				.withPromocao(dto.isPromocao())
				.withValorPromo(dto.getValorPromo())
				.withImagem(dto.getImagem())
				.withCategoria(dto.getCategoria())
				.withCodigoProduto(dto.getCodigoProduto())
				.withQuantidade(dto.getQuantidade())
				.withFornecedor(fornecedor)
				.build();
	}

	private boolean fornecedorHasId(ProdutoDTORequest dto) {
		return dto.getFornecedor().getId() != null;
	}

	private boolean dtoHasFornecedor(ProdutoDTORequest dto) {
		return dto.getFornecedor() != null;
	}

}