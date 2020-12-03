package com.gft.desafioapi.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;
import com.gft.desafioapi.utils.EntityUtils;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	FornecedorService fornecedorService;

	public Produto findProdutoById(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Produto criar(Produto produto) {

		EntityUtils.setIdNull(produto);

		checkFornecedor(produto);

		validatePromocao(produto);

		fornecedorService.findFornecedorById(produto.getFornecedor().getId());

		return produtoRepository.save(produto);
	}

	public Produto update(Long id, Produto produto) {

		Produto produtoAtualizado = produto.coalesce(findProdutoById(id), id);

		validatePromocao(produtoAtualizado);

		return produtoRepository.save(produtoAtualizado);
	}

	private void validatePromocao(Produto produto) {
		if (!produto.isPromocao() && Objects.nonNull(produto.getValorPromo())) {
			throw new DataIntegrityViolationException(
					"Se o produto não estiver em promoção, seu valorPromo deve ser igual null");
		}

		if (produto.isPromocao() && Objects.isNull(produto.getValorPromo())) {
			throw new DataIntegrityViolationException("Se o produto estiver em promoção, valorPromo é obrigatório");
		}
	}

	private void checkFornecedor(Produto produto) {
		if (Objects.isNull(produto.getFornecedor())) {
			throw new EmptyResultDataAccessException("Fornecedor não informado", 1);
		}
	}

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		produtoRepository.deleteById(id);

		return new ResponseEntity<Map<String, Boolean>>(Map.of("success", true), HttpStatus.OK);
	}
}
