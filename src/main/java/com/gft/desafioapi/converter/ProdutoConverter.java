package com.gft.desafioapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.ProdutoDTO;
import com.gft.desafioapi.model.Produto;

@Component
public class ProdutoConverter {

	public ProdutoDTO entityToDto(Produto produto) {

		return ProdutoDTO.builder()
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

	public List<ProdutoDTO> entityToDto(List<Produto> produtos) {
		return produtos.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	public Page<ProdutoDTO> entityToDto(Page<Produto> produtos) {
		return produtos.map(this::entityToDto);
	}

	public Produto dtoToEntity(ProdutoDTO dto) {
		return Produto.builder()
				.withId(dto.getId())
				.withNome(dto.getNome())
				.withValor(dto.getValor())
				.withPromocao(dto.isPromocao())
				.withValorPromo(dto.getValorPromo())
				.withImagem(dto.getImagem())
				.withCategoria(dto.getCategoria())
				.withCodigoProduto(dto.getCodigoProduto())
				.withQuantidade(dto.getQuantidade())
				.withFornecedor(dto.getFornecedor())
				.build();
	}

	public List<Produto> dtoToEntity(List<ProdutoDTO> dtos) {
		return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

}
