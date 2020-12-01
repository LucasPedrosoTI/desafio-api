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

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.service.FornecedorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Fornecedores")
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	FornecedorService fornecedorService;

	@ApiOperation("Lista todos os fornecedores")
	@GetMapping
	public Page<Fornecedor> listarFornecedores(Pageable pageable) {
		return fornecedorRepository.findAll(pageable);
	}

	@ApiOperation("Retorna um fornecedor por ID")
	@GetMapping("/{id}")
	public Fornecedor encontrarClientePorId(@PathVariable Long id) {
		return fornecedorService.findClienteById(id);
	}

	@ApiOperation("Cadastra um novo fornecedor")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Fornecedor criarCliente(@RequestBody @Valid Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

}
