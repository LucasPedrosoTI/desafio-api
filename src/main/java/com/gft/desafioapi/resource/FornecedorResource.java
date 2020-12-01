package com.gft.desafioapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.FornecedorRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

	@Autowired
	FornecedorRepository fornecedorRepository;

	@ApiOperation("Lista todos os fornecedores")
	@GetMapping
	public Page<Fornecedor> listarFornecedores(Pageable pageable) {
		return fornecedorRepository.findAll(pageable);
	}

}
