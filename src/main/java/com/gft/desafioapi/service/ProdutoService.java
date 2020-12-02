package com.gft.desafioapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	FornecedorService fornecedorService;

	public Produto findClienteById(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Produto criar(Produto produto) {

		if (produto.getFornecedor() == null) {
			throw new EmptyResultDataAccessException("ID do fornecedor não informado", 1);
		}

		fornecedorService.findFornecedorById(produto.getFornecedor().getId());

		return produtoRepository.save(produto);
	}

}
