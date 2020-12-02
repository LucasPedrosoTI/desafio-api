package com.gft.desafioapi.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;
import com.gft.desafioapi.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	ProdutoService produtoService;

	@ApiOperation("Lista todos os produtos")
	@GetMapping
	public Page<Produto> listarProdutos(Pageable pageable) {
		return produtoRepository.findAll(pageable);
	}

	@ApiOperation("Retorna um produto por ID")
	@GetMapping("/{id}")
	public Produto encontrarProdutoPorId(@PathVariable Long id) {
		return produtoService.findClienteById(id);
	}

	@ApiOperation("Cadastra um novo produto")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Produto criarProduto(@RequestBody @Valid Produto produto) {

		return produtoService.criar(produto);
	}

}
