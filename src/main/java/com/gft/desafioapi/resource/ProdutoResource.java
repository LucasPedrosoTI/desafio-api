package com.gft.desafioapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;

	@ApiOperation("Lista todos os produtos")
	@GetMapping
	public Page<Produto> listarProdutos(Pageable pageable) {
		return produtoRepository.findAll(pageable);
	}

}
