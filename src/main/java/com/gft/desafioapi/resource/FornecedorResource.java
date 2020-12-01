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

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.repository.filter.FornecedorFilter;
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
	public Page<Fornecedor> listarFornecedores(FornecedorFilter filter, Pageable pageable) {
		return fornecedorService.findAllWithFilter(filter, pageable);
	}

	@ApiOperation("Retorna um fornecedor por ID")
	@GetMapping("/{id}")
	public Fornecedor encontrarFornecedorPorId(@PathVariable Long id) {
		return fornecedorService.findFornecedorById(id);
	}

	@ApiOperation("Cadastra um novo fornecedor")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Fornecedor criarFornecedor(@RequestBody @Valid Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	@ApiOperation("Atualiza os dados de um fornecedor por ID")
	@PutMapping("/{id}")
	public Fornecedor atualizarFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
		return fornecedorService.update(id, fornecedor);
	}

	@ApiOperation("Exclui um fornecedor por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirForneecedor(@PathVariable Long id) {
		return fornecedorService.delete(id);
	}

	@ApiOperation("Lista os fornecedores em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<Fornecedor> listarFornecedoresAsc(Pageable pageable) {
		return fornecedorRepository.findAllOrderByNomeAsc(pageable);
	}

	@ApiOperation("Lista os fornecedores em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<Fornecedor> listarFornecedoresDesc(Pageable pageable) {
		return fornecedorRepository.findAllOrderByNomeDesc(pageable);
	}

	@ApiOperation("Busca fornecedores por nome")
	@GetMapping("/nome/{nome}")
	public Page<Fornecedor> encontrarFornecedoresPorNome(@PathVariable String nome, Pageable pageable) {
		return fornecedorRepository.findByNomeContaining(nome, pageable);
	}

}
