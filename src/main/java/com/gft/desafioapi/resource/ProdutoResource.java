package com.gft.desafioapi.resource;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return produtoService.findProdutoById(id);
	}

	@ApiOperation("Cadastra um novo produto")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Produto criarProduto(@RequestBody @Valid Produto produto) {

		return produtoService.criar(produto);
	}

	@ApiOperation("Atualiza os dados de um pruduto por ID")
	@PutMapping("/{id}")
	public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
		return produtoService.update(id, produto);
	}

	@ApiOperation("Exclui um produto por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirProduto(@PathVariable Long id) {
		return produtoService.delete(id);
	}

	@ApiOperation("Lista os produtos em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<Produto> listarProdutosAsc(Pageable pageable) {
		return produtoRepository.findAllOrderByNomeAsc(pageable);
	}

	@ApiOperation("Lista os produtos em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<Produto> listarProdutosDesc(Pageable pageable) {
		return produtoRepository.findAllOrderByNomeDesc(pageable);
	}

	@ApiOperation("Busca produtos por nome")
	@GetMapping("/nome/{nome}")
	public Page<Produto> encontrarProdutoPorNome(@PathVariable String nome, Pageable pageable) {
		return produtoRepository.findByNomeContaining(nome, pageable);
	}

}
