package com.gft.desafioapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Vendas")
@RestController
@RequestMapping("/api/vendas")
public class VendaResource {

	@Autowired
	VendaRepository vendaRepository;

	@Autowired
	VendaService vendaService;

	@ApiOperation("Lista todas as vendas")
	@GetMapping
	public Page<Venda> listarVendas(Pageable pageable) {
		return vendaRepository.findAll(pageable);
	}

	@ApiOperation("Retorna uma venda por ID")
	@GetMapping("/{id}")
	public Venda encontrarVendaPorId(@PathVariable Long id) {
		return vendaService.findProdutoById(id);
	}

}
